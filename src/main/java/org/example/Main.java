package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static String URL = "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";
    public static void main(String[] args) {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
                        .setSocketTimeout(30000)    // максимальное время ожидания получения данных
                        .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                        .build())
                .build();

        try (CloseableHttpResponse response = httpClient.execute(new HttpGet(URL))) {
          //  Scanner sc = new Scanner(response.getEntity().getContent());
          //  while (sc.hasNext()) {
          //      System.out.println(sc.nextLine());
          //  }
            ObjectMapper mapper = new ObjectMapper();
            List<CatFacts> facts = mapper.readValue(response.getEntity().getContent(),
                    new TypeReference<List<CatFacts>>() {});
            facts.stream()
                    .filter(fact -> fact.getUpvotes() > 0)
                    .forEach(System.out::println);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        //  HttpGet request = new HttpGet("https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats");
      //  CloseableHttpResponse response = httpClient.execute(request);
    }
}