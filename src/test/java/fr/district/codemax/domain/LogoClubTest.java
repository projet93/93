package fr.district.codemax.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.district.codemax.web.rest.TestUtil;

public class LogoClubTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LogoClub.class);
        LogoClub logoClub1 = new LogoClub();
        logoClub1.setId(1L);
        LogoClub logoClub2 = new LogoClub();
        logoClub2.setId(logoClub1.getId());
        assertThat(logoClub1).isEqualTo(logoClub2);
        logoClub2.setId(2L);
        assertThat(logoClub1).isNotEqualTo(logoClub2);
        logoClub1.setId(null);
        assertThat(logoClub1).isNotEqualTo(logoClub2);
    }
}
