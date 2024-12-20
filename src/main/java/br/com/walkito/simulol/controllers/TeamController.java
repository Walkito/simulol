package br.com.walkito.simulol.controllers;

import br.com.walkito.simulol.models.team.TeamRequest;
import br.com.walkito.simulol.services.TeamService;
import br.com.walkito.simulol.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "team")
public class TeamController {

    @Autowired
    TeamService teamService;

    @GetMapping
    public ApiResponse getTeam(@RequestParam(name = "userId") String userId,
                               @RequestParam(name = "sessionId") int sessionId,
                               @RequestParam(name = "teamId") int teamId){
        return teamService.getTeam(userId, sessionId, teamId);
    }

    @PostMapping(path = "/create")
    public ApiResponse createTeam(@RequestBody TeamRequest teamRequest){
        return teamService.createTeam(teamRequest);
    }

    @PutMapping(path = "/edit")
    public ApiResponse editTeam(@RequestBody TeamRequest teamRequest){
        return teamService.editTeam(teamRequest);
    }

    @DeleteMapping(path = "/delete")
    public ApiResponse deleteTeam(@RequestParam(name = "userId") String userId,
                                  @RequestParam(name = "gameSessionId") int gameSessionId,
                                  @RequestParam(name = "teamId") int teamId){
        return teamService.deleteTeam(userId, gameSessionId, teamId);
    }
}
