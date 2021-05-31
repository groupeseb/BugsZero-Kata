package com.adaptionsoft.games.uglytrivia;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PlayerTest {

    @Test
    public void fillPurse_should_increment_purse() {
        Player player = Player.builder().purse(5).build();

        player.fillPurse();

        assertThat(player.getPurse(), is(6));
    }

    @Test
    public void move_should_increment_place() {
        Player player = Player.builder().place(2).build();

        player.move(6);

        assertThat(player.getPlace(), is(8));
    }

    @Test
    public void move_when_too_far_should_reset_place() {
        Player player = Player.builder().place(2).build();

        player.move(10);

        assertThat(player.getPlace(), is(0));
    }

    @Test
    public void doIWin_given_less_than_6_coins_should_return_false(){
        Player player = Player.builder().purse(5).build();

        assertThat(player.doIWin(), is(false));
    }

    @Test
    public void doIWin_given_6_coins_should_return_true(){
        Player player = Player.builder().purse(6).build();

        assertThat(player.doIWin(), is(true));
    }
}
