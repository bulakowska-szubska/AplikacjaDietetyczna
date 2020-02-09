package com.vistula.aplikacja.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.vistula.aplikacja.web.rest.TestUtil;

public class PrzepisSkladnikiTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrzepisSkladniki.class);
        PrzepisSkladniki przepisSkladniki1 = new PrzepisSkladniki();
        przepisSkladniki1.setId(1L);
        PrzepisSkladniki przepisSkladniki2 = new PrzepisSkladniki();
        przepisSkladniki2.setId(przepisSkladniki1.getId());
        assertThat(przepisSkladniki1).isEqualTo(przepisSkladniki2);
        przepisSkladniki2.setId(2L);
        assertThat(przepisSkladniki1).isNotEqualTo(przepisSkladniki2);
        przepisSkladniki1.setId(null);
        assertThat(przepisSkladniki1).isNotEqualTo(przepisSkladniki2);
    }
}
