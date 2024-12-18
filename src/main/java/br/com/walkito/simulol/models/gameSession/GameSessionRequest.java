package br.com.walkito.simulol.models.gameSession;

public class GameSessionRequest extends GameSession{

    GameSession gameSession;

    String idUser;

    public GameSessionRequest() {
    }

    public GameSession getGameSession() {
        return gameSession;
    }

    public void setGameSession(GameSession gameSession) {
        this.gameSession = gameSession;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
