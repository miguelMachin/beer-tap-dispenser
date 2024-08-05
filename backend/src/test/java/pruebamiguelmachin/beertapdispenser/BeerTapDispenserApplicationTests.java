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
import pruebamiguelmachin.beertapdispenser.infraestructura.adapter.exception.DispenserException;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerTapDispenserApplicationTests {

    @Autowired
    DispenserService dispenserService;

    @Test
    void contextLoads() {
    }

    /*Services*/
    @Test
    void testDispenserServiceCreateDispenser() {
        DispenserDto dispenserDto = dispenserService.createDispenser(0.3f, 1.0f);
        assertThat(dispenserDto.getFlow_volume())
                .isEqualTo(0.3f);
    }

    @Test
    void testDispenserServiceFindById() {
        DispenserDto dispenserDto = dispenserService.findDispenser("fe749b90-5f8c-4ba7-82e2-1331bfff7dcb");
        assertThat(dispenserDto.getId())
                .isEqualTo("fe749b90-5f8c-4ba7-82e2-1331bfff7dcb");
    }

    @Test
    void testDispenserServiceGetAllDispenser() {
        List<DispenserDto> dispenserDto = dispenserService.findAll();

        assertThat(dispenserDto.size())
                .isGreaterThan(0);
    }

    @Test
    void testDispenserServiceFindByIdNotFound() {
        assertThrows(DispenserException.class, () -> dispenserService.findDispenser("cb0825b0-9666-4fb9-ac79-c5125c332b7"));

    }

    //The following tests must run one after the other, care with order of execution

    @Test
    void testDispenserServiceUpdateStatusCloseException() throws ParseException {
        DispenserStatusDto dispenserStatusDto = new DispenserStatusDto();
        dispenserStatusDto.setStatus(DispenserStatusEnum.CLOSE.ordinal());
        dispenserStatusDto.setUpdated_at("2024-08-04T01:42:15.781Z");

        assertThrows(DispenserException.class, () -> dispenserService.updateStatus("fe749b90-5f8c-4ba7-82e2-1331bfff7dcb", dispenserStatusDto));
    }

    @Test
    void testDispenserServiceUpdateStatusOpen() throws ParseException {
        DispenserStatusDto dispenserStatusDto = new DispenserStatusDto();
        dispenserStatusDto.setStatus(DispenserStatusEnum.OPEN.ordinal());
        dispenserStatusDto.setUpdated_at("2024-08-04T01:42:15Z");
        dispenserService.updateStatus("fe749b90-5f8c-4ba7-82e2-1331bfff7dcb", dispenserStatusDto);
        DispenserDto dispenserDto = dispenserService.findDispenser("fe749b90-5f8c-4ba7-82e2-1331bfff7dcb");

        assertAll(
                "Grouped Assertions of DispenserUpdateStatus open",
                () -> assertEquals(DispenserStatusEnum.OPEN.ordinal(), dispenserDto.getStatus(), "Status should be 1"),
                () -> assertThat(dispenserDto.getUsages().size()).isGreaterThan(0),
                () -> assertNull(dispenserDto.getUsages().get(0).getClose_at(), "Close Date should be null")
        );
    }

    @Test
    void testDispenserServiceUpdateStatusClose() throws ParseException {
        DispenserStatusDto dispenserStatusDto = new DispenserStatusDto();
        dispenserStatusDto.setStatus(DispenserStatusEnum.CLOSE.ordinal());
        dispenserStatusDto.setUpdated_at("2024-08-04T01:42:25Z");
        dispenserService.updateStatus("fe749b90-5f8c-4ba7-82e2-1331bfff7dcb", dispenserStatusDto);
        DispenserDto dispenserDto = dispenserService.findDispenser("fe749b90-5f8c-4ba7-82e2-1331bfff7dcb");

        assertAll(
                "Grouped Assertions of DispenserUpdateStatus close",
                () -> assertEquals(DispenserStatusEnum.CLOSE.ordinal(), dispenserDto.getStatus(), "Status should be 1"),
                () -> assertThat(dispenserDto.getUsages().size()).isGreaterThan(0),
                () -> assertNotNull(dispenserDto.getUsages().get(0).getClose_at(), "Close Date dont should be null"),
                () -> assertEquals(10, dispenserDto.getAmount(), "Amount should be 10")
        );
    }


    @Test
    void testDispenserServiceUpdateStatusCloseNotFoundUsage() throws ParseException {
        DispenserStatusDto dispenserStatusDto = new DispenserStatusDto();
        dispenserStatusDto.setStatus(DispenserStatusEnum.CLOSE.ordinal());
        dispenserStatusDto.setUpdated_at("2024-08-04T01:42:25Z");

        assertThrows(DispenserException.class, () -> dispenserService.updateStatus("fe749b90-5f8c-4ba7-82e2-1331bfff7dcb", dispenserStatusDto));

    }


    @Test
    void testDispenserServiceFindDispenserSpending() {
        DispenserSpendingDto dispenserSpendingDto = dispenserService.findDispenserSpending("fe749b90-5f8c-4ba7-82e2-1331bfff7dcb");
        assertAll(
                "Grouped Assertions of DispenserSpending",
                () -> assertThat(dispenserSpendingDto.getUsages().size()).isGreaterThan(0),
                () -> assertEquals(10, dispenserSpendingDto.getAmount(), "Amount should be 10")
        );

    }


    /*Utils*/
	@Test
	void testUtilsLongDateToString() throws ParseException {
		String dateString = Utils.longDateToString(Long.parseLong("1704088800000"));
        assertThat(dateString)
                .isEqualTo("2024-01-01T07:00:00Z");

	}

    @Test
    void testUtilsStringToDate() throws ParseException {
        Date date = Utils.stringToDate("2024-01-01T07:00:00Z");
        assertThat(date.toString())
                .isEqualTo("Mon Jan 01 07:00:00 CET 2024");

    }
}