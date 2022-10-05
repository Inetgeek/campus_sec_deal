/**
 * <p>
 * 账户钱包表 服务实现类
 * </p>
 *
 * @author Colyn
 * @since 2022-09-18
 */

package edu.njust.campus_sec_deal.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.njust.campus_sec_deal.entity.CampusWallet;
import edu.njust.campus_sec_deal.mapper.CampusWalletMapper;
import edu.njust.campus_sec_deal.service.CampusWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CampusWalletServiceImpl extends ServiceImpl<CampusWalletMapper, CampusWallet> implements CampusWalletService {

    @Autowired
    CampusWalletMapper walletMapper;

    @Override
    public CampusWallet getWallet(String uid) {
        return walletMapper.selectById(uid);
    }

    @Override
    public boolean updateWallet(String wid, float add) {
        UpdateWrapper<CampusWallet> update = new UpdateWrapper<>();
        CampusWallet wallet = new CampusWallet();
        update.lambda().eq(CampusWallet::getWalletId, wid).setSql("wallet_balance=wallet_balance+" + add);
        try {
            walletMapper.update(wallet, update);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isRightPwd(String wid, int pwd) {
        return pwd == walletMapper.selectById(wid).getWalletPwd();
    }

    @Override
    public boolean modifyPwd(String wid, int pwd) {
        try {
            CampusWallet wallet = new CampusWallet()
                    .setWalletId(wid)
                    .setWalletPwd(pwd);
            walletMapper.updateById(wallet);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
