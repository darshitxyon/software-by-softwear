package com.sbs.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.sbs.web.rest.TestUtil;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class BuyerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Buyer.class);
        Buyer buyer1 = new Buyer();
        buyer1.setId(UUID.randomUUID());
        Buyer buyer2 = new Buyer();
        buyer2.setId(buyer1.getId());
        assertThat(buyer1).isEqualTo(buyer2);
        buyer2.setId(UUID.randomUUID());
        assertThat(buyer1).isNotEqualTo(buyer2);
        buyer1.setId(null);
        assertThat(buyer1).isNotEqualTo(buyer2);
    }
}
