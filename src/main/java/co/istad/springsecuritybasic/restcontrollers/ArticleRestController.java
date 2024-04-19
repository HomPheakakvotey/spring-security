package co.istad.springsecuritybasic.restcontrollers;

import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/articles")
@RestController
public class ArticleRestController {

    @GetMapping
    public String getAllArticles(){
        return "getting all articles";
    }

    @GetMapping("/read/{id}")
    public String readSingleArticle(@PathVariable int id){
        return "getting one artcile" + id;
    }

    @PostMapping("/write")
    public String createArticle(){
        return "Creating article";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteArticle(@PathVariable int id){
        return "Deleting article" + id;
    }

}
