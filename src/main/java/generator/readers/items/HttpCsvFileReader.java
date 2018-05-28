package generator.readers.items;

import generator.utility.Tuple;
import lombok.*;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HttpCsvFileReader {

    public List<Tuple<String, Double>> getJsonItems(String path) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://csv-items-generator.herokuapp.com/items";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<JsonItem[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, JsonItem[].class);

        return Arrays
                .stream(response.getBody())
                .map( x -> new Tuple<String, Double>(x.getName(), x.getPrice()))
                .collect(Collectors.toList());
    }

    public List<Tuple<String, Double>> getXmlItems(String path) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://csv-items-generator.herokuapp.com/items";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_XML));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<XmlResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, XmlResponse.class);

        return Arrays.stream(response.getBody().getItem())
                .map( x -> new Tuple<String, Double>(x.getName(), x.getPrice()))
                .collect(Collectors.toList());
    }

    public List<Tuple<String, Double>> getYamlItems(String path) {
        return null;
    }

    @Value
    static class JsonItem{
        private String name;
        private double price;
    }

    @XmlRootElement(name = "List")
    @NoArgsConstructor
    @Setter
    @Getter
    @ToString
    static class XmlResponse{
        private XmlItem[] item;
    }

    @XmlRootElement(name = "item")
    @NoArgsConstructor
    @Setter
    @Getter
    @ToString
    static class XmlItem implements Serializable {
        private String name;
        private double price;
    }

}
