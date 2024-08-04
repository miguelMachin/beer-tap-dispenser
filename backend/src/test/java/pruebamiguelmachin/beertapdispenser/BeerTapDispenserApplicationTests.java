package pruebamiguelmachin.beertapdispenser;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pruebamiguelmachin.beertapdispenser.application.usercases.DispenserService;
import pruebamiguelmachin.beertapdispenser.application.utils.Utils;
import pruebamiguelmachin.beertapdispenser.domain.model.dto.DispenserDto;
import pruebamiguelmachin.beertapdispenser.domain.model.dto.DispenserSpendingDto;
import pruebamiguelmachin.beertapdispenser.domain.model.dto.DispenserStatusDto;
import pruebamiguelmachin.beertapdispenser.domain.model.enums.DispenserStatusEnum;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

@SpringBootTest
class BeerTapDispenserApplicationTests {
/*
	@Autowired
	DispenserService dispenserService;
@Test
	void contextLoads() {
	}

	@Test
	void testDispenserServiceCreateDispenser(){
		DispenserDto dispenserDto = dispenserService.createDispenser(0.3f, 1.0f);
		System.out.println(dispenserDto);
	}

	@Test
	void testDispenserServiceFindById() {
		DispenserDto dispenserDto = dispenserService.findDispenser("cb0825b0-9666-4fb9-ac79-c5125c332b7e");
		System.out.println(dispenserDto);
	}*/

	@Test
	void testDispenserServiceFindByIdSpending() throws ParseException {
		/*DispenserSpendingDto dispenserSpendingDto = dispenserService.findDispenserSpending("f4b94168-e520-44ef-a6b0-21ebcfa568a2");
		System.out.println(dispenserSpendingDto);*/

    //     simpleDateFormat.format(calendar.getTime());

        /*String date_string = "2024-01-01 07:00:00";
        //Instantiating the SimpleDateFormat class
        //SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //Parsing the given String to Date object
        Date date = formatter.parse(date_string);
        System.out.println("Date value: "+date);
        System.out.println("Date value: "+date.toString());
        System.out.println("Date value: "+date.getTime());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong("1704088800000"));
        System.out.println(formatter.format(calendar.getTime()));
        System.out.println(formatter.format(date.getTime()));*/

        //System.out.println(Utils.longDateToString(Long.parseLong("1704088800000")));
		//System.out.println(Utils.stringToDate("2024-01-01T06:00:20Z"));




	}


}
