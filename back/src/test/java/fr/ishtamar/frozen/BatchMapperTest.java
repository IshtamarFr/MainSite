package fr.ishtamar.frozen;

import fr.ishtamar.TestContent;
import fr.ishtamar.frozen.model.batch.BatchDto;
import fr.ishtamar.frozen.model.batch.BatchMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BatchMapperTest {
    @Autowired
    BatchMapper batchMapper;

    @Test
    @DisplayName("Le mapping imbriqué fonctionne correctement")
    void testBatchToDto(){
        //Given
        TestContent tc=new TestContent();
        //When
        String batchDto=batchMapper.toDto(tc.batch1food1).toString();
        //Then
        assertThat(batchDto).contains("Boeuf bourguignon","cuit","GN");
    }
}
