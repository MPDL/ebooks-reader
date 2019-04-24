package de.mpg.mpdl.ebooksreader.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.mpg.mpdl.ebooksreader.model.dto.DocDTO;

public class JacksonUtil {

    public static String stringifyDocDTO(DocDTO docDTO) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonStr = mapper.writeValueAsString(docDTO);
            return jsonStr;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";

    }

    public static DocDTO parseDocDTO(String docDTOStr) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            DocDTO docDTO = mapper.readValue(docDTOStr, DocDTO.class);
            return docDTO;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new DocDTO();
    }

    public static String stringifyDocDTOList(List<DocDTO> list){
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonStr = mapper.writeValueAsString(list);
            return jsonStr;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static List<DocDTO> parseDocDTOList(String docDTOListStr) {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            List<DocDTO> docDTOList = mapper.readValue(docDTOListStr, mapper.getTypeFactory().constructCollectionType(List.class, DocDTO.class));
            return docDTOList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

}
