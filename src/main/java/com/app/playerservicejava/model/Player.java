package com.app.playerservicejava.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="PLAYERS")
public class Player {

    @Id
    @Column(name = "PLAYERID")
    @GeneratedValue(strategy = GenerationType.UUID)
    @NotBlank(message = "Player ID cannot be blank")
    private String playerId;

    @Column(name = "BIRTHYEAR")
    @Pattern(regexp = "\\d{4}", message = "Birth year must be a 4-digit number")
    private String birthYear;

    @Column(name = "BIRTHMONTH")
    @Pattern(regexp = "(0?[1-9]|1[0-2])", message = "Birth month must be 1-12")
    private String birthMonth;

    @Column(name = "BIRTHDAY")
    @Pattern(regexp = "(0?[1-9]|[12][0-9]|3[01])", message = "Birth day must be 1-31")
    private String birthDay;

    @Column(name = "BIRTHCOUNTRY")
    private String birthCountry;

    @Column(name = "BIRTHSTATE")
    private String birthState;

    @Column(name = "BIRTHCITY")
    private String birthCity;

    @Column(name = "DEATHYEAR")
    private String deathYear;

    @Column(name = "DEATHMONTH")
    private String deathMonth;

    @Column(name = "DEATHDAY")
    private String deathDay;

    @Column(name = "DEATHCOUNTRY")
    private String deathCountry;

    @Column(name = "DEATHSTATE")
    private String deathState;

    @Column(name = "DEATHCITY")
    private String deathCity;

    @Column(name = "NAMEFIRST")
    @Size(max = 50, message = "First name cannot exceed 50 characters")
    private String firstName;

    @Column(name = "NAMELAST")
    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name cannot exceed 50 characters")
    private String lastName;

    @Column(name = "NAMEGIVEN")
    private String givenName;

    @Column(name = "WEIGHT")
    @Pattern(regexp = "\\d{1,3}", message = "Weight must be a number between 1-999")
    private String weight;

    @Column(name = "HEIGHT")
    @Pattern(regexp = "\\d{1,3}", message = "Height must be a number between 1-999")
    private String height;

    @Column(name = "BATS")
    @Pattern(regexp = "[LRB]", message = "Bats must be L, R, or B")
    private String bats;

    @Column(name = "THROWS")
    @Pattern(regexp = "[LR]", message = "Throws must be L or R")
    private String throwStats;

    @Column(name = "DEBUT")
    private String debut;

    @Column(name = "FINALGAME")
    private String finalGame;

    @Column(name = "RETROID")
    private String retroId;

    @Column(name = "BBREFID")
    private String bbrefId;

    public Player() {}

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public String getBirthMonth() {
        return birthMonth;
    }

    public void setBirthMonth(String birthMonth) {
        this.birthMonth = birthMonth;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getBirthCountry() {
        return birthCountry;
    }

    public void setBirthCountry(String birthCountry) {
        this.birthCountry = birthCountry;
    }

    public String getBirthState() {
        return birthState;
    }

    public void setBirthState(String birthState) {
        this.birthState = birthState;
    }

    public String getBirthCity() {
        return birthCity;
    }

    public void setBirthCity(String birthCity) {
        this.birthCity = birthCity;
    }

    public String getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(String deathYear) {
        this.deathYear = deathYear;
    }

    public String getDeathMonth() {
        return deathMonth;
    }

    public void setDeathMonth(String deathMonth) {
        this.deathMonth = deathMonth;
    }

    public String getDeathDay() {
        return deathDay;
    }

    public void setDeathDay(String deathDay) {
        this.deathDay = deathDay;
    }

    public String getDeathCountry() {
        return deathCountry;
    }

    public void setDeathCountry(String deathCountry) {
        this.deathCountry = deathCountry;
    }

    public String getDeathState() {
        return deathState;
    }

    public void setDeathState(String deathState) {
        this.deathState = deathState;
    }

    public String getDeathCity() {
        return deathCity;
    }

    public void setDeathCity(String deathCity) {
        this.deathCity = deathCity;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getBats() {
        return bats;
    }

    public void setBats(String bats) {
        this.bats = bats;
    }

    public String getThrowStats() {
        return throwStats;
    }

    public void setThrowStats(String throwStats) {
        this.throwStats = throwStats;
    }

    public String getDebut() {
        return debut;
    }

    public void setDebut(String debut) {
        this.debut = debut;
    }

    public String getFinalGame() {
        return finalGame;
    }

    public void setFinalGame(String finalGame) {
        this.finalGame = finalGame;
    }

    public String getRetroId() {
        return retroId;
    }

    public void setRetroId(String retroId) {
        this.retroId = retroId;
    }

    public String getBbrefId() {
        return bbrefId;
    }

    public void setBbrefId(String bbrefId) {
        this.bbrefId = bbrefId;
    }
}
