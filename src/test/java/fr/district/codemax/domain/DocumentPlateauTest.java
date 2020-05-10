package fr.district.codemax.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.district.codemax.web.rest.TestUtil;

public class DocumentPlateauTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocumentPlateau.class);
        DocumentPlateau documentPlateau1 = new DocumentPlateau();
        documentPlateau1.setId(1L);
        DocumentPlateau documentPlateau2 = new DocumentPlateau();
        documentPlateau2.setId(documentPlateau1.getId());
        assertThat(documentPlateau1).isEqualTo(documentPlateau2);
        documentPlateau2.setId(2L);
        assertThat(documentPlateau1).isNotEqualTo(documentPlateau2);
        documentPlateau1.setId(null);
        assertThat(documentPlateau1).isNotEqualTo(documentPlateau2);
    }
}
