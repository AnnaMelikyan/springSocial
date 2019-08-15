package com.example.springsocial;


import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

  private Facebook facebook;
  private ConnectionRepository connectionRepository;

  public MainController(Facebook facebook, ConnectionRepository connectionRepository) {
    this.facebook = facebook;
    this.connectionRepository = connectionRepository;
  }


  @GetMapping("/")
  public String facebookPage(Model model) {
    if (connectionRepository.findPrimaryConnection(Facebook.class) == null) {
      return "redirect:/";
    }
    model.addAttribute(facebook.userOperations().getUserProfile());
    PagedList <Post> homeFeed = facebook.feedOperations().getHomeFeed();
    model.addAttribute("feed", homeFeed);
    return "index";
  }
}
