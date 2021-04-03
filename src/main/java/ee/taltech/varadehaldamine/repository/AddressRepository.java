package ee.taltech.varadehaldamine.repository;

import ee.taltech.varadehaldamine.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, String> {

    Address findAddressByAssetId(String assetId);
}
