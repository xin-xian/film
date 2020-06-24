package org.gec.service.impl;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.gec.mapper.FilminfoMapper;
import org.gec.pojo.Filminfo;
import org.gec.pojo.FilminfoExample;
import org.gec.service.FilmInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.jsp.tagext.TagFileInfo;
import java.util.List;

@Service
@Transactional
public class FilmInfoServiceImpl implements FilmInfoService {

    @Autowired
    private FilminfoMapper filminfoMapper;

    @Override
 public List<Filminfo> findAllInfo(Filminfo filminfo) {
        System.out.println("filminfo:"+ filminfo);
        //查询
        return filminfoMapper.queryInfos(filminfo);
    }

    @Override
    public void save(Filminfo filminfo) {

        filminfoMapper.insert(filminfo);
    }


    @Override
    public void deleteByIds(int[] filmIds) {
        for (int ids:filmIds ) {
            filminfoMapper.deleteByPrimaryKey(ids);
        }
    }

    @Override
    public List<Filminfo> findAllInfo2(FilminfoExample filminfo) {
        List<Filminfo> filminfos = filminfoMapper.selectByExample(filminfo);
        return filminfos;
    }

    @Override
    public boolean checkName(String filmname) {
        FilminfoExample filminfoExample = new FilminfoExample();
        FilminfoExample.Criteria criteria = filminfoExample.createCriteria();
        criteria.andFilmnameEqualTo(filmname);
        List<Filminfo> filminfos = filminfoMapper.selectByExample(filminfoExample);
        if(filminfos!=null&&filminfos.size()>0){
            return true;
        }
        return false;
    }

    @Override
    public Filminfo selectByPrimary(Integer filmIds) {
        Filminfo filminfo = filminfoMapper.selectByPrimaryKey(filmIds);
        return filminfo;
    }

}
