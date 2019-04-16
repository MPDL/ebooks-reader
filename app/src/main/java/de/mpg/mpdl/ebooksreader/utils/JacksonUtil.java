package de.mpg.mpdl.ebooksreader.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import de.mpg.mpdl.ebooksreader.model.dto.DocDTO;

public class JacksonUtil {

    public static String stringfyDocDTO(DocDTO docDTO) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonStr = mapper.writeValueAsString(docDTO);                   // write to string
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
}
