package org.leeln.config.server;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.annotation.ObservableExecutionMode;
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author liuzhenyuan
 * @version Last modified 16/9/27
 */
public class ATest {

    private HystrixCommand hystrixCommand;
    private DefaultProperties defaultProperties;


    private HystrixProperty hp(String name, String value) {
        return new HystrixProperty() {
            @Override
            public Class<? extends Annotation> annotationType() {
                return HystrixProperty.class;
            }

            @Override
            public String name() {
                return name;
            }

            @Override
            public String value() {
                return value;
            }
        };
    }

    @Test
    public void name() throws Exception {
        hystrixCommand = new HystrixCommand() {
            @Override
            public String groupKey() {
                return null;
            }

            @Override
            public String commandKey() {
                return "commandKey";
            }

            @Override
            public String threadPoolKey() {
                return "threadPoolKey";
            }

            @Override
            public String fallbackMethod() {
                return "fallbackMethod";
            }

            @Override
            public HystrixProperty[] commandProperties() {
                return new HystrixProperty[] {
                        hp("1", "1000"),
                        hp("2", "2000"),
                        hp("3", "3000"),
                        hp("4", "4000"),
                        hp("5", "5000")
                };
            }

            @Override
            public HystrixProperty[] threadPoolProperties() {
                return new HystrixProperty[0];
            }

            @Override
            public Class<? extends Throwable>[] ignoreExceptions() {
                return null;
            }

            @Override
            public ObservableExecutionMode observableExecutionMode() {
                return null;
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return HystrixCommand.class;
            }
        };

        defaultProperties = new DefaultProperties() {
            @Override
            public String groupKey() {
                return null;
            }

            @Override
            public String threadPoolKey() {
                return null;
            }

            @Override
            public HystrixProperty[] commandProperties() {
                return new HystrixProperty[]{
                        hp("a", "A"),
                        hp("b", "B"),
                        hp("c", "C"),
                        hp("d", "D"),
                        hp("1", "1001"),
                        hp("e", "E")
                };
            }

            @Override
            public HystrixProperty[] threadPoolProperties() {
                return new HystrixProperty[0];
            }

            @Override
            public Class<? extends Throwable>[] ignoreExceptions() {
                return null;
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return DefaultProperties.class;
            }
        };



        List<HystrixProperty> list = getCommandProperties();


        for (HystrixProperty hystrixProperty : list) {
            System.out.println(hystrixProperty.name() + ":" + hystrixProperty.value());
        }
        System.out.println(list);
    }

    private static final Function identityFun = new Function<Object, Object>() {
        @Nullable
        @Override
        public Object apply(@Nullable Object input) {
            return input;
        }
    };

    public List<HystrixProperty> getCommandProperties() {

        return getOrDefault(new Supplier<List<HystrixProperty>>() {
            @Override
            public List<HystrixProperty> get() {
                return ImmutableList.copyOf(hystrixCommand.commandProperties());
            }
        }, new Supplier<List<HystrixProperty>>() {
            @Override
            public List<HystrixProperty> get() {
                return hasDefaultProperties()
                        ? ImmutableList.copyOf(defaultProperties.commandProperties())
                        : Collections.<HystrixProperty>emptyList();
            }
        }, this.<HystrixProperty>nonEmptyList());
    }

    public boolean hasDefaultProperties() {
        return defaultProperties != null;
    }

    private String get(String key, String defaultKey) {
        return StringUtils.isNotBlank(key) ? key : defaultKey;
    }

    private <T> Predicate<List<T>> nonEmptyList() {
        return new Predicate<List<T>>() {
            @Override
            public boolean apply(@Nullable List<T> input) {
                return input != null && !input.isEmpty();
            }
        };
    }

    @SuppressWarnings("unchecked")
    private <T> T getOrDefault(Supplier<T> source, Supplier<T> defaultChoice, Predicate<T> isDefined) {
        return getOrDefault(source, defaultChoice, isDefined, (Function<T, T>) identityFun);
    }

    private <T> T getOrDefault(Supplier<T> source, Supplier<T> defaultChoice, Predicate<T> isDefined, Function<T, T> map) {
        T res = source.get();
        T defaultRes = defaultChoice.get();

        if (res instanceof List && defaultChoice instanceof List ) {
            ((Collection) defaultChoice).addAll((Collection) res);
        } else if (isDefined.apply(res)) {
            defaultRes = res;
        }

        return map.apply(defaultRes);
    }
}
