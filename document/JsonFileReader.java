package com.powernode;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class JsonFileReader {

    private static final int MAX_READ_SIZE = 10000; // 每次读取的最大字符数

    public static void main(String[] args) {
        String filePath = "E:\\IdeaProjects\\JavaFile\\File-001\\src\\main\\resources\\your_large_json_file.json";
        // 替换为你的JSON文件路径
        try {
            readJsonInChunks(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readJsonInChunks(String filePath) throws IOException {
        // 使用BufferedReader来按行读取文件
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            JsonFactory factory = new JsonFactory();
            JsonParser parser = factory.createParser(reader);

            StringBuilder buffer = new StringBuilder();
            long totalCharsRead = 0;

            // 遍历JSON文件的内容
            while (!parser.isClosed()) {
                JsonToken token = parser.nextToken();

                // 如果有JSON字段存在，读取它
                if (token != null) {
                    switch (token) {
                        case FIELD_NAME:
                            String fieldName = parser.getCurrentName();
                            System.out.println("Field name: " + fieldName);
                            break;

                        case VALUE_STRING:
                            String fieldValue = parser.getValueAsString();
                            System.out.println("Field value: " + fieldValue);
                            break;

                        // 其他类型的JSON值（如数字、布尔值、数组、对象等）也可以根据需求处理
                        case VALUE_NUMBER_INT:
                        case VALUE_NUMBER_FLOAT:
                        case VALUE_TRUE:
                        case VALUE_FALSE:
                            System.out.println("Field value: " + parser.getText());
                            break;

                        // 如果遇到新的数据，处理读取并清空缓存
                        case START_OBJECT:
                        case START_ARRAY:
                            break;
                        case END_OBJECT:
                        case END_ARRAY:
                            break;
                    }
                }

                // 读取最大10000个字符
                if (buffer.length() > MAX_READ_SIZE) {
                    System.out.println("Buffer exceeds max size, flushing data...");
                    buffer.setLength(0); // 清空缓冲区
                    totalCharsRead += MAX_READ_SIZE;
                }
            }

            System.out.println("Finished reading the JSON file.");
        }
    }
}
