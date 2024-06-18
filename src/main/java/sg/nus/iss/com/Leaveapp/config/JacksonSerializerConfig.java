package sg.nus.iss.com.Leaveapp.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 全局序列化与反序列化配置
 */
@Configuration
public class JacksonSerializerConfig {
    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String TIME_PATTERN = "HH:mm:ss";

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern(DATE_PATTERN);
        return builder -> {
            // 反序列化时未知属性不抛出异常
            ObjectMapper objectMapper = builder.createXmlMapper(false).build();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            // 日期
            builder.simpleDateFormat(DATE_TIME_PATTERN);
            // 序列化
            builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(formatter));
            builder.serializerByType(LocalDate.class, new LocalDateSerializer(formatter2));
            // 反序列化
            builder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));
            builder.deserializerByType(LocalDate.class, new LocalDateDeserializer(formatter2));
        };
    }
}
