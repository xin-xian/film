package org.gec.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.gec.pojo.Filminfo;
import org.gec.pojo.FilminfoExample;
import org.gec.pojo.Filmtype;
import org.gec.service.FilmInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

@Controller
public class FilmInfoController {

    @Autowired
    private FilmInfoService filmInfoService;

    public List<Filminfo> getResult() {
        return result;
    }

    //电影list
    List<Filminfo> result ;

    //每页显示数量
   public static final Integer PAGESIZE = 2;

    @RequestMapping("/findFilms")
    public ModelAndView findFilms(Filminfo film,FilminfoExample filminfo, Integer typeid,
                @RequestParam(value = "pageNum",required = false,defaultValue = "1")
                                    Integer pageNum) throws Exception{
        FilminfoExample.Criteria criteria = filminfo.createCriteria();
        //选了类型
        if (typeid != 0){
            criteria.equals(typeid);
        }
        //判断
        if(StringUtils.isNotEmpty(film.getFilmname())){
            criteria.andFilmnameLike("%"+film.getFilmname()+"%");
        }

        if(StringUtils.isNotEmpty(film.getActor())){
            criteria.andActorLike("%"+film.getActor()+"%");
        }

        if(StringUtils.isNotEmpty(film.getDirector())){
            criteria.andDirectorLike("%"+film.getDirector()+"%");
        }

        if(film.getBigprice() != null && film.getBigprice() != 0 ){
            criteria.andTicketpriceLessThanOrEqualTo(new BigDecimal(film.getBigprice()));
        }

        if(film.getSmallprice() != null && film.getSmallprice() != 0){
            criteria.andTicketpriceGreaterThanOrEqualTo(new BigDecimal(film.getSmallprice()));
        }
        //设置分页
        PageHelper.startPage(pageNum,PAGESIZE);
        //电影条件
        //查询
        result = filmInfoService.findAllInfo2(filminfo);

        for(Filminfo info:result){
            System.out.println(info.getFilmtype().getTypename());
        }

        ModelAndView mv = new ModelAndView();

        PageInfo info = new PageInfo(result);

        mv.addObject( "info",info);
        mv.addObject("result",result);
        mv.addObject("typeid",typeid);
        mv.setViewName("page/result");
        return mv;
    }
//    @RequestMapping("/findFilms")
//    public ModelAndView findFilms(Filminfo filminfo, Integer typeid) throws Exception{
//        //选了类型
//        if (typeid != 0){
//            Filmtype type = new Filmtype();
//            type.setTypeid(typeid);
//
//            filminfo.setFilmtype(type);
//        }
//
//
//        //电影条件
//        //查询
//        result = filmInfoService.findAllInfo(filminfo);
//
//        for(Filminfo info:result){
//            System.out.println(info.getFilmtype().getTypename());
//        }
//
//        ModelAndView mv = new ModelAndView();
//
//        mv.addObject("result",result);
//        mv.setViewName("page/result");
//        return mv;
//    }

    @RequestMapping("/FilmAddServlet")
    public String FilmAddServlet(Integer typeid, @Validated Filminfo filminfo, BindingResult result,
                                 Model model, MultipartFile picname) throws Exception{
        //判断是否有误
        if(result.hasErrors()){
            //循环获得错误
            List<FieldError> fieldError = result.getFieldErrors();
            for (FieldError error:fieldError ) {
                model.addAttribute(error.getField(),error.getDefaultMessage());
            }
            return "forward:/toAddFilm";
        }


        //电影类型
        Filmtype filmtype = new Filmtype();
        filmtype.setTypeid(typeid);

        //上传的文件名
        String fileName = picname.getOriginalFilename();

        //文件存储路径

        File file = new File("J:\\粤嵌Java\\",fileName);

        filminfo.setPic(fileName);

        //写到磁盘
        picname.transferTo(file);

        //查询
        filmInfoService.save(filminfo);

        return "redirect:/toAddFilm";
    }
    @RequestMapping("/checkName")
    @ResponseBody
    public String checkName(String filmname){
        boolean result = filmInfoService.checkName(filmname);
        System.out.println("result:"+result);
        return result+"";
    }

    //批量删除
    @RequestMapping("/deleteFilms")
    public String deleteFilms(int[] filmIds){
        //调用方法删除
        filmInfoService.deleteByIds(filmIds);

        //重定向
        return "redirect:/toCinema";
    }
    @RequestMapping("/queryDetails/{filmid}/{filmname}")
    @ResponseBody
    public Filminfo queryDetails(@PathVariable("filmid") Integer filmIds,
                                 @PathVariable("filmname") String filmname){
        System.out.println("filmname"+filmname);
        Filminfo filminfo = filmInfoService.selectByPrimary(filmIds);
        return filminfo;
    }

}
