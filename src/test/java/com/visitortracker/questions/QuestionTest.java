package com.visitortracker.questions;

import com.visitortracker.model.Visit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
public class QuestionTest {

    @Autowired
    protected Question1 question1;
    @Autowired
    protected Question2 question2;


    @Test
    public void loadLog_forEmptyFileName_ShouldReturnNoValue() {

        assertThat(question1.loadLog(null)).isNull();
    }

    @Test
    public void loadLog_forFileNameWithWrongExtension_ShouldReturnNoValue() {

        assertThat(question1.loadLog("log.ab")).isNull();
    }

    @Test
    public void loadLog_forUniqueProductUser_shouldReturnUniqueUsers() {

        List<Visit> visitList = question1.loadLog("src/main/resources/productVisitorCount.log");

        assertThat(visitList).hasSizeGreaterThan(3);
    }

    @Test
    public void loadLog_forWrongDataFormat_shouldReturnException() {

        var exception = assertThrows(RuntimeException.class, () -> {
            question1.loadLog("src/main/resources/productVisitorCountWithWrongFormat.log");
        });

        assertThat(exception.getMessage()).contains("out of bounds for");
    }

    @Test
    public void countNumberOfHomepageVisits_forDateFromTo_shouldReturnCount() {
        String startDate = "2019-01-02 06";
        String endDate = "2019-01-05 02";
        long result = question1.countNumberOfHomepageVisits("src/main/resources/productVisitorCount.log", startDate, endDate);

        assertThat(result).isGreaterThan(3);
    }

    @Test
    public void sendMessageLetterLeftToRight_forGoodEncryptedMessage_shouldReturnReadByNumber() {
        String res = question2.sendMessageLetterLeftToRight(5, "toioynnkpheleaigshareconhtomesnlewx");

        assertThat(res).isEqualTo("theresnoplacelikehomeonasnowynightx");
    }
}
