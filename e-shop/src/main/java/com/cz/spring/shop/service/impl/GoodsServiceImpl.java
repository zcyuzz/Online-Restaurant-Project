package com.cz.spring.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cz.spring.common.PageParam;
import com.cz.spring.common.PageResult;
import com.cz.spring.shop.dao.GoodsMapper;
import com.cz.spring.shop.model.Goods;
import com.cz.spring.shop.service.GoodsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public PageResult<Goods> listByType(PageParam page) {

        List<Goods> list = baseMapper.listByType(page);

        return new PageResult<>(list, page.getTotal());
    }

    @Override
    public PageResult<Goods> listFull(PageParam page) {

        List<Goods> list = baseMapper.listFull(page);

        return new PageResult<>(list, page.getTotal());
    }

	/* (non-Javadoc)
	 * @see com.cz.spring.shop.service.GoodsService#list(com.cz.spring.common.PageParam)
	 */
	@Override
	public PageResult<Goods> list(PageParam pageParam) {
	
		List<Goods> goodsList=baseMapper.list(pageParam);
		return new PageResult<>(goodsList, pageParam.getTotal());
	}
}
