package br.com.walkito.simulol.controllers;

import br.com.walkito.simulol.models.team.TeamRequest;
import br.com.walkito.simulol.services.TeamService;
import br.com.walkito.simulol.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "team")
public class TeamController {

    @Autowired
    TeamService teamService;

    @PostMapping(path = "/create")
    public ApiResponse createTeam(@RequestBody TeamRequest teamRequest){
        return teamService.createTeam(teamRequest);
    }

}
