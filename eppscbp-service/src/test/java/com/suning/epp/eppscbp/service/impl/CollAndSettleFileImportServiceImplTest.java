/**
 * 
 */
package com.suning.epp.eppscbp.service.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.suning.epp.eppscbp.dto.req.CollAndSettleApplyDto;
import com.suning.epp.eppscbp.dto.res.ApiResDto;
import com.suning.epp.eppscbp.dto.res.CollAndSettleResDto;
import com.suning.epp.eppscbp.rsf.service.UnifiedReceiptService;

/**
 * @author 17080704
 *
 */
public class CollAndSettleFileImportServiceImplTest {

    @InjectMocks
    CollAndSettleFileImportServiceImpl collAndSettleFileImportServiceImpl;

    @Mock
    UnifiedReceiptService unifiedReceiptService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test1() {
        ApiResDto<CollAndSettleResDto> res = new ApiResDto<CollAndSettleResDto>();
        Mockito.when(unifiedReceiptService.submitCollSettleOrder(Mockito.any(CollAndSettleApplyDto.class))).thenReturn(res);
        CollAndSettleApplyDto apply = new CollAndSettleApplyDto();
        apply.setReceiveAmt("1");
        collAndSettleFileImportServiceImpl.submitSettleOrder(apply);

    }

    @Test
    public void test2() {
        ApiResDto<CollAndSettleResDto> res = new ApiResDto<CollAndSettleResDto>();
        Mockito.when(unifiedReceiptService.submitCollSettleOrder(Mockito.any(CollAndSettleApplyDto.class))).thenReturn(res);
        CollAndSettleApplyDto apply = new CollAndSettleApplyDto();
        apply.setReceiveAmt("1;1;1");
        collAndSettleFileImportServiceImpl.submitSettleOrder(apply);

    }

}
