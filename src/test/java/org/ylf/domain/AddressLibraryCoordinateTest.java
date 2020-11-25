package org.ylf.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.ylf.web.rest.TestUtil;

public class AddressLibraryCoordinateTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AddressLibraryCoordinate.class);
        AddressLibraryCoordinate addressLibraryCoordinate1 = new AddressLibraryCoordinate();
        addressLibraryCoordinate1.setId(1L);
        AddressLibraryCoordinate addressLibraryCoordinate2 = new AddressLibraryCoordinate();
        addressLibraryCoordinate2.setId(addressLibraryCoordinate1.getId());
        assertThat(addressLibraryCoordinate1).isEqualTo(addressLibraryCoordinate2);
        addressLibraryCoordinate2.setId(2L);
        assertThat(addressLibraryCoordinate1).isNotEqualTo(addressLibraryCoordinate2);
        addressLibraryCoordinate1.setId(null);
        assertThat(addressLibraryCoordinate1).isNotEqualTo(addressLibraryCoordinate2);
    }
}
