package com.vistula.aplikacja.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vistula.aplikacja.web.rest.TestUtil;

public class SkladnikiTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Skladniki.class);
        Skladniki skladniki1 = new Skladniki();
        skladniki1.setId(1L);
        Skladniki skladniki2 = new Skladniki();
        skladniki2.setId(skladniki1.getId());
        assertThat(skladniki1).isEqualTo(skladniki2);
        skladniki2.setId(2L);
        assertThat(skladniki1).isNotEqualTo(skladniki2);
        skladniki1.setId(null);
        assertThat(skladniki1).isNotEqualTo(skladniki2);
    }
}
