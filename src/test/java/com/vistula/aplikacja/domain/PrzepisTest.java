package com.vistula.aplikacja.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vistula.aplikacja.web.rest.TestUtil;

public class PrzepisTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Przepis.class);
        Przepis przepis1 = new Przepis();
        przepis1.setId(1L);
        Przepis przepis2 = new Przepis();
        przepis2.setId(przepis1.getId());
        assertThat(przepis1).isEqualTo(przepis2);
        przepis2.setId(2L);
        assertThat(przepis1).isNotEqualTo(przepis2);
        przepis1.setId(null);
        assertThat(przepis1).isNotEqualTo(przepis2);
    }
}
