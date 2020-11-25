package org.ylf.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.ylf.web.rest.TestUtil;

public class LogisticsCenterTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LogisticsCenter.class);
        LogisticsCenter logisticsCenter1 = new LogisticsCenter();
        logisticsCenter1.setId(1L);
        LogisticsCenter logisticsCenter2 = new LogisticsCenter();
        logisticsCenter2.setId(logisticsCenter1.getId());
        assertThat(logisticsCenter1).isEqualTo(logisticsCenter2);
        logisticsCenter2.setId(2L);
        assertThat(logisticsCenter1).isNotEqualTo(logisticsCenter2);
        logisticsCenter1.setId(null);
        assertThat(logisticsCenter1).isNotEqualTo(logisticsCenter2);
    }
}
