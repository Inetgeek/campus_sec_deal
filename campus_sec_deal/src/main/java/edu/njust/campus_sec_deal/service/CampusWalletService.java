/**
 * <p>
 * 账户钱包表 服务类
 * </p>
 *
 * @author Colyn
 * @since 2022-09-18
 */

package edu.njust.campus_sec_deal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.njust.campus_sec_deal.entity.CampusWallet;


public interface CampusWalletService extends IService<CampusWallet> {

    CampusWallet getWallet(String uid);

    boolean updateWallet(String wid, float add);

    boolean isRightPwd(String wid, int pwd);

    boolean modifyPwd(String wid, int pwd);
}
