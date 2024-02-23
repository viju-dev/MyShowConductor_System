package com.example.MyShowConductor_System.Converters;

import com.example.MyShowConductor_System.EntryDTOs.ShowEntryDTO;
import com.example.MyShowConductor_System.Enums.FormatEnum;
import com.example.MyShowConductor_System.Entities.Show;
import com.example.MyShowConductor_System.ResponseDTOs.ShowResponseDTO;

import java.util.ArrayList;
import java.util.List;

public class ShowConvertors {
    public static Show convertEntryToEntity(ShowEntryDTO showEntryDTO){

//        List<ScreenTypeEnum> screenTypeEnums = showEntryDTO.getScreenType();
//        String screenTypes = showEntryDTO.getScreenType();
//        String screen = "";
//        for (ScreenTypeEnum st:screenTypeEnums){
//            screenType += st.name()+",";
//        }

//        System.out.println(showEntryDTO.getScreenType());
//        ScreenTypeEnum[] screenTypes = showEntryDTO.getScreenType();
//        for (ScreenTypeEnum str:screenTypes){
//            screen += str.name()+",";
//            System.out.println(screen);
//        }
//        System.out.println(screen);
//
//        System.out.println(screen);


        String screenType ="";
        for (FormatEnum str:showEntryDTO.getFormats()){ //converting languageEnum to Language string
            screenType += str.name()+",";
//            lang.concat(str.name()+",");
        }
        Show show = Show.builder().showDate(showEntryDTO.getShowDate())
                .showTime(showEntryDTO.getShowTime())
                .formats(screenType)
                .build();
        return show;
    }
    public static ShowResponseDTO convertEntityToResponse(Show show){

//        List<ScreenTypeEnum> screenTypeEnums = showEntryDTO.getScreenType();
//        String screenTypes = showEntryDTO.getScreenType();
//        String screen = "";
//        for (ScreenTypeEnum st:screenTypeEnums){
//            screenType += st.name()+",";
//        }

//        System.out.println(showEntryDTO.getScreenType());
//        ScreenTypeEnum[] screenTypes = showEntryDTO.getScreenType();
//        for (ScreenTypeEnum str:screenTypes){
//            screen += str.name()+",";
//            System.out.println(screen);
//        }
//        System.out.println(screen);
//
//        System.out.println(screen);
        String screenEnum = show.getFormats();
        List<FormatEnum> enumList = new ArrayList<>();
        for (String screen:screenEnum.split(",")){
            enumList.add(FormatEnum.valueOf(screen));
        }

    ShowResponseDTO showResponseDTO = ShowResponseDTO.builder()
            .showDate(show.getShowDate())
            .showTime(show.getShowTime())
            .screenTypeEnums(enumList)
            .movieName(show.getMovie().getTitle())
            .theatreName(show.getTheatre().getName())
            .build();
        return showResponseDTO;
    }
}
