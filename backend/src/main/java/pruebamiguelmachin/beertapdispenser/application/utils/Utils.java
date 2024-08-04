package pruebamiguelmachin.beertapdispenser.application.utils;

import pruebamiguelmachin.beertapdispenser.domain.model.constants.DispenserConstants;
import pruebamiguelmachin.beertapdispenser.infraestructura.adapter.entities.DispenserEntity;
import pruebamiguelmachin.beertapdispenser.infraestructura.adapter.entities.DispenserUsageEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static float calculateAmount(DispenserEntity dispenserEntity){
        float amount = 0.0f;
        if (dispenserEntity.getUsages() != null) {
            for(DispenserUsageEntity dispenserUsage: dispenserEntity.getUsages()) {
                amount += dispenserUsage.getTotalSpent();

            }
        }
        return amount;
    }

    public static String longDateToString(Long time) {
        SimpleDateFormat formatter = new SimpleDateFormat(DispenserConstants.FORMAT_DATE);
        Date date = new Date(time);
        return formatter.format(date.getTime());
    }

    public static Date stringToDate(String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(DispenserConstants.FORMAT_DATE);
        return formatter.parse(date);
    }


}
