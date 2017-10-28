package dzevako.betcore;

import org.junit.Assert;
import org.junit.Test;

import dzevako.betcore.game.basketball.BasketballScore;
import dzevako.betcore.game.hockey.HockeyScore;
import dzevako.betcore.game.volleyball.VolleyballScore;

/**
 * Тесты для счета
 * @author dzevako
 * @since Aug 18, 2015
 */
public class ScoreTest
{
    @Test
    public void testBasketballScore1()
    {
        String str = "6 мин Счёт: 25:13 (25:13)";
        BasketballScore score = BasketballScore.get(str);
        Assert.assertEquals(1, score.getSet());
        Assert.assertEquals(12, score.getDiff());
        Assert.assertEquals(12, score.getDiff(1));
        Assert.assertEquals(0, score.getDiff(2));
        Assert.assertEquals(0, score.getDiff(3));
        Assert.assertEquals(0, score.getDiff(4));
        Assert.assertEquals(0, score.getDiff(5));
        Assert.assertEquals(25, score.getFirstPoints());
        Assert.assertEquals(13, score.getSecondPoints());
        Assert.assertEquals(13, score.getMinPoints());
        Assert.assertEquals(25, score.getMaxPoints());
        Assert.assertEquals(38, score.getTotal());
        Assert.assertEquals(38, score.getTotal(1));
        Assert.assertEquals(0, score.getTotal(2));
        Assert.assertEquals(0, score.getTotal(3));
        Assert.assertEquals(0, score.getTotal(4));
        Assert.assertEquals(0, score.getTotal(5));
        Assert.assertEquals(0, score.getWinner(1));
        Assert.assertEquals(0, score.getWinner(2));
        Assert.assertEquals(0, score.getWinner(3));
        Assert.assertEquals(0, score.getWinner(4));
        Assert.assertEquals(0, score.getWinner(5));
        Assert.assertEquals(6, score.getTime());
        Assert.assertEquals("25:13", score.getMainScore());
        Assert.assertEquals(25, score.getGameFirstPoints());
        Assert.assertEquals(13, score.getGameSecondPoints());
        Assert.assertEquals(12, score.getGameDiff());
    }

    @Test
    public void testBasketballScore2()
    {
        String str = "6 мин Счёт: 25:13";
        BasketballScore score = BasketballScore.get(str);
        Assert.assertEquals(1, score.getSet());
        Assert.assertEquals(12, score.getDiff());
        Assert.assertEquals(12, score.getDiff(1));
        Assert.assertEquals(0, score.getDiff(2));
        Assert.assertEquals(0, score.getDiff(3));
        Assert.assertEquals(0, score.getDiff(4));
        Assert.assertEquals(0, score.getDiff(5));
        Assert.assertEquals(25, score.getFirstPoints());
        Assert.assertEquals(13, score.getSecondPoints());
        Assert.assertEquals(13, score.getMinPoints());
        Assert.assertEquals(25, score.getMaxPoints());
        Assert.assertEquals(38, score.getTotal());
        Assert.assertEquals(38, score.getTotal(1));
        Assert.assertEquals(0, score.getTotal(2));
        Assert.assertEquals(0, score.getTotal(3));
        Assert.assertEquals(0, score.getTotal(4));
        Assert.assertEquals(0, score.getTotal(5));
        Assert.assertEquals(0, score.getWinner(1));
        Assert.assertEquals(0, score.getWinner(2));
        Assert.assertEquals(0, score.getWinner(3));
        Assert.assertEquals(0, score.getWinner(4));
        Assert.assertEquals(0, score.getWinner(5));
        Assert.assertEquals(6, score.getTime());
        Assert.assertEquals("25:13", score.getMainScore());
        Assert.assertEquals(25, score.getGameFirstPoints());
        Assert.assertEquals(13, score.getGameSecondPoints());
        Assert.assertEquals(12, score.getGameDiff());
    }

    @Test
    public void testBasketballScore3()
    {
        String str = "8 мин Счёт: 55:43 (30:17, 25:26)";
        BasketballScore score = BasketballScore.get(str);
        Assert.assertEquals(2, score.getSet());
        Assert.assertEquals(1, score.getDiff());
        Assert.assertEquals(13, score.getDiff(1));
        Assert.assertEquals(1, score.getDiff(2));
        Assert.assertEquals(0, score.getDiff(3));
        Assert.assertEquals(0, score.getDiff(4));
        Assert.assertEquals(0, score.getDiff(5));
        Assert.assertEquals(25, score.getFirstPoints());
        Assert.assertEquals(26, score.getSecondPoints());
        Assert.assertEquals(25, score.getMinPoints());
        Assert.assertEquals(26, score.getMaxPoints());
        Assert.assertEquals(98, score.getTotal());
        Assert.assertEquals(47, score.getTotal(1));
        Assert.assertEquals(51, score.getTotal(2));
        Assert.assertEquals(0, score.getTotal(3));
        Assert.assertEquals(0, score.getTotal(4));
        Assert.assertEquals(0, score.getTotal(5));
        Assert.assertEquals(1, score.getWinner(1));
        Assert.assertEquals(0, score.getWinner(2));
        Assert.assertEquals(0, score.getWinner(3));
        Assert.assertEquals(0, score.getWinner(4));
        Assert.assertEquals(0, score.getWinner(5));
        Assert.assertEquals(8, score.getTime());
        Assert.assertEquals("55:43", score.getMainScore());
        Assert.assertEquals(55, score.getGameFirstPoints());
        Assert.assertEquals(43, score.getGameSecondPoints());
        Assert.assertEquals(12, score.getGameDiff());
    }

    @Test
    public void testBasketballScore4()
    {
        String str = "5 мин Счёт: 72:72 (30:17, 25:20, 10:15, 7:20)";
        BasketballScore score = BasketballScore.get(str);
        Assert.assertEquals(4, score.getSet());
        Assert.assertEquals(13, score.getDiff());
        Assert.assertEquals(13, score.getDiff(1));
        Assert.assertEquals(5, score.getDiff(2));
        Assert.assertEquals(5, score.getDiff(3));
        Assert.assertEquals(13, score.getDiff(4));
        Assert.assertEquals(0, score.getDiff(5));
        Assert.assertEquals(7, score.getFirstPoints());
        Assert.assertEquals(20, score.getSecondPoints());
        Assert.assertEquals(7, score.getMinPoints());
        Assert.assertEquals(20, score.getMaxPoints());
        Assert.assertEquals(144, score.getTotal());
        Assert.assertEquals(47, score.getTotal(1));
        Assert.assertEquals(45, score.getTotal(2));
        Assert.assertEquals(25, score.getTotal(3));
        Assert.assertEquals(27, score.getTotal(4));
        Assert.assertEquals(0, score.getTotal(5));
        Assert.assertEquals(1, score.getWinner(1));
        Assert.assertEquals(1, score.getWinner(2));
        Assert.assertEquals(2, score.getWinner(3));
        Assert.assertEquals(0, score.getWinner(4));
        Assert.assertEquals(0, score.getWinner(5));
        Assert.assertEquals(5, score.getTime());
        Assert.assertEquals("72:72", score.getMainScore());
        Assert.assertEquals(72, score.getGameFirstPoints());
        Assert.assertEquals(72, score.getGameSecondPoints());
        Assert.assertEquals(0, score.getGameDiff());
    }

    @Test
    public void testBasketballScore5()
    {
        String str = "2 мин Счёт: 84:85 (30:17, 25:20, 10:15, 17:30)(ОТ 2:3)";
        BasketballScore score = BasketballScore.get(str);
        Assert.assertEquals(5, score.getSet());
        Assert.assertEquals(1, score.getDiff());
        Assert.assertEquals(13, score.getDiff(1));
        Assert.assertEquals(5, score.getDiff(2));
        Assert.assertEquals(5, score.getDiff(3));
        Assert.assertEquals(13, score.getDiff(4));
        Assert.assertEquals(1, score.getDiff(5));
        Assert.assertEquals(2, score.getFirstPoints());
        Assert.assertEquals(3, score.getSecondPoints());
        Assert.assertEquals(2, score.getMinPoints());
        Assert.assertEquals(3, score.getMaxPoints());
        Assert.assertEquals(169, score.getTotal());
        Assert.assertEquals(47, score.getTotal(1));
        Assert.assertEquals(45, score.getTotal(2));
        Assert.assertEquals(25, score.getTotal(3));
        Assert.assertEquals(47, score.getTotal(4));
        Assert.assertEquals(5, score.getTotal(5));
        Assert.assertEquals(1, score.getWinner(1));
        Assert.assertEquals(1, score.getWinner(2));
        Assert.assertEquals(2, score.getWinner(3));
        Assert.assertEquals(2, score.getWinner(4));
        Assert.assertEquals(0, score.getWinner(5));
        Assert.assertEquals(2, score.getTime());
        Assert.assertEquals("84:85", score.getMainScore());
        Assert.assertEquals(84, score.getGameFirstPoints());
        Assert.assertEquals(85, score.getGameSecondPoints());
        Assert.assertEquals(1, score.getGameDiff());
    }

    @Test
    public void testBasketballScore6()
    {
        String str = "2 мин Счёт: 84:85 (30:17, 25:20, 10:15, 17:30) ОТ(2:3)";
        BasketballScore score = BasketballScore.get(str);
        Assert.assertEquals(5, score.getSet());
        Assert.assertEquals(1, score.getDiff());
        Assert.assertEquals(13, score.getDiff(1));
        Assert.assertEquals(5, score.getDiff(2));
        Assert.assertEquals(5, score.getDiff(3));
        Assert.assertEquals(13, score.getDiff(4));
        Assert.assertEquals(1, score.getDiff(5));
        Assert.assertEquals(2, score.getFirstPoints());
        Assert.assertEquals(3, score.getSecondPoints());
        Assert.assertEquals(2, score.getMinPoints());
        Assert.assertEquals(3, score.getMaxPoints());
        Assert.assertEquals(169, score.getTotal());
        Assert.assertEquals(47, score.getTotal(1));
        Assert.assertEquals(45, score.getTotal(2));
        Assert.assertEquals(25, score.getTotal(3));
        Assert.assertEquals(47, score.getTotal(4));
        Assert.assertEquals(5, score.getTotal(5));
        Assert.assertEquals(1, score.getWinner(1));
        Assert.assertEquals(1, score.getWinner(2));
        Assert.assertEquals(2, score.getWinner(3));
        Assert.assertEquals(2, score.getWinner(4));
        Assert.assertEquals(0, score.getWinner(5));
        Assert.assertEquals(2, score.getTime());
        Assert.assertEquals("84:85", score.getMainScore());
        Assert.assertEquals(84, score.getGameFirstPoints());
        Assert.assertEquals(85, score.getGameSecondPoints());
        Assert.assertEquals(1, score.getGameDiff());
    }

    @Test
    public void testHockeyScore1()
    {
        String str = "6 мин Счёт: 1:0";
        HockeyScore score = HockeyScore.get(str);
        Assert.assertEquals(1, score.getSet());
        Assert.assertEquals(1, score.getDiff());
        Assert.assertEquals(1, score.getDiff(1));
        Assert.assertEquals(0, score.getDiff(2));
        Assert.assertEquals(0, score.getDiff(3));
        Assert.assertEquals(0, score.getDiff(4));
        Assert.assertEquals(0, score.getDiff(5));
        Assert.assertEquals(1, score.getFirstPoints());
        Assert.assertEquals(0, score.getSecondPoints());
        Assert.assertEquals(0, score.getMinPoints());
        Assert.assertEquals(1, score.getMaxPoints());
        Assert.assertEquals(1, score.getTotal());
        Assert.assertEquals(1, score.getTotal(1));
        Assert.assertEquals(0, score.getTotal(2));
        Assert.assertEquals(0, score.getTotal(3));
        Assert.assertEquals(0, score.getTotal(4));
        Assert.assertEquals(0, score.getTotal(5));
        Assert.assertEquals(0, score.getWinner(1));
        Assert.assertEquals(0, score.getWinner(2));
        Assert.assertEquals(0, score.getWinner(3));
        Assert.assertEquals(0, score.getWinner(4));
        Assert.assertEquals(0, score.getWinner(5));
        Assert.assertEquals(6, score.getTime());
        Assert.assertEquals("1:0", score.getMainScore());
        Assert.assertEquals(1, score.getGameFirstPoints());
        Assert.assertEquals(0, score.getGameSecondPoints());
        Assert.assertEquals(1, score.getGameDiff());
    }

    @Test
    public void testHockeyScore2()
    {
        String str = "35 мин Счёт: 3:4 (1:2)";
        HockeyScore score = HockeyScore.get(str);
        Assert.assertEquals(2, score.getSet());
        Assert.assertEquals(0, score.getDiff());
        Assert.assertEquals(1, score.getDiff(1));
        Assert.assertEquals(0, score.getDiff(2));
        Assert.assertEquals(0, score.getDiff(3));
        Assert.assertEquals(0, score.getDiff(4));
        Assert.assertEquals(0, score.getDiff(5));
        Assert.assertEquals(2, score.getFirstPoints());
        Assert.assertEquals(2, score.getSecondPoints());
        Assert.assertEquals(2, score.getMinPoints());
        Assert.assertEquals(2, score.getMaxPoints());
        Assert.assertEquals(7, score.getTotal());
        Assert.assertEquals(3, score.getTotal(1));
        Assert.assertEquals(4, score.getTotal(2));
        Assert.assertEquals(0, score.getTotal(3));
        Assert.assertEquals(0, score.getTotal(4));
        Assert.assertEquals(0, score.getTotal(5));
        Assert.assertEquals(2, score.getWinner(1));
        Assert.assertEquals(0, score.getWinner(2));
        Assert.assertEquals(0, score.getWinner(3));
        Assert.assertEquals(0, score.getWinner(4));
        Assert.assertEquals(0, score.getWinner(5));
        Assert.assertEquals(35, score.getTime());
        Assert.assertEquals("3:4", score.getMainScore());
        Assert.assertEquals(3, score.getGameFirstPoints());
        Assert.assertEquals(4, score.getGameSecondPoints());
        Assert.assertEquals(1, score.getGameDiff());
    }

    @Test
    public void testHockeyScore3()
    {
        String str = "63 мин Счёт: 6:6 (1:2, 3:2, 2:2)";
        HockeyScore score = HockeyScore.get(str);
        Assert.assertEquals(4, score.getSet());
        Assert.assertEquals(0, score.getDiff());
        Assert.assertEquals(1, score.getDiff(1));
        Assert.assertEquals(1, score.getDiff(2));
        Assert.assertEquals(0, score.getDiff(3));
        Assert.assertEquals(0, score.getDiff(4));
        Assert.assertEquals(0, score.getDiff(5));
        Assert.assertEquals(0, score.getFirstPoints());
        Assert.assertEquals(0, score.getSecondPoints());
        Assert.assertEquals(0, score.getMinPoints());
        Assert.assertEquals(0, score.getMaxPoints());
        Assert.assertEquals(12, score.getTotal());
        Assert.assertEquals(3, score.getTotal(1));
        Assert.assertEquals(5, score.getTotal(2));
        Assert.assertEquals(4, score.getTotal(3));
        Assert.assertEquals(0, score.getTotal(4));
        Assert.assertEquals(0, score.getTotal(5));
        Assert.assertEquals(2, score.getWinner(1));
        Assert.assertEquals(1, score.getWinner(2));
        Assert.assertEquals(0, score.getWinner(3));
        Assert.assertEquals(0, score.getWinner(4));
        Assert.assertEquals(0, score.getWinner(5));
        Assert.assertEquals(63, score.getTime());
        Assert.assertEquals("6:6", score.getMainScore());
        Assert.assertEquals(6, score.getGameFirstPoints());
        Assert.assertEquals(6, score.getGameSecondPoints());
        Assert.assertEquals(0, score.getGameDiff());
    }

    @Test
    public void testVolleyballScore1()
    {
        String str = "0:0 (2:5)";
        VolleyballScore score = VolleyballScore.get(str);
        Assert.assertEquals(1, score.getSet());
        Assert.assertEquals(3, score.getDiff());
        Assert.assertEquals(3, score.getDiff(1));
        Assert.assertEquals(0, score.getDiff(2));
        Assert.assertEquals(0, score.getDiff(3));
        Assert.assertEquals(0, score.getDiff(4));
        Assert.assertEquals(0, score.getDiff(5));
        Assert.assertEquals(0, score.getFirstWins());
        Assert.assertEquals(0, score.getSecondWins());
        Assert.assertEquals(2, score.getFirstPoints());
        Assert.assertEquals(5, score.getSecondPoints());
        Assert.assertEquals(2, score.getMinPoints());
        Assert.assertEquals(5, score.getMaxPoints());
        Assert.assertEquals(20, score.getOverPoints());
        Assert.assertEquals(25, score.getSetPoints());
        Assert.assertEquals(7, score.getTotal());
        Assert.assertEquals(7, score.getTotal(1));
        Assert.assertEquals(0, score.getTotal(2));
        Assert.assertEquals(0, score.getTotal(3));
        Assert.assertEquals(0, score.getTotal(4));
        Assert.assertEquals(0, score.getTotal(5));
        Assert.assertEquals(0, score.getWinner(1));
        Assert.assertEquals(0, score.getWinner(2));
        Assert.assertEquals(0, score.getWinner(3));
        Assert.assertEquals(0, score.getWinner(4));
        Assert.assertEquals(0, score.getWinner(5));
    }

    @Test
    public void testVolleyballScore2()
    {
        String str = "1:0 (25:21, 16:16)";
        VolleyballScore score = VolleyballScore.get(str);
        Assert.assertEquals(2, score.getSet());
        Assert.assertEquals(0, score.getDiff());
        Assert.assertEquals(4, score.getDiff(1));
        Assert.assertEquals(0, score.getDiff(2));
        Assert.assertEquals(0, score.getDiff(3));
        Assert.assertEquals(0, score.getDiff(4));
        Assert.assertEquals(0, score.getDiff(5));
        Assert.assertEquals(1, score.getFirstWins());
        Assert.assertEquals(0, score.getSecondWins());
        Assert.assertEquals(16, score.getFirstPoints());
        Assert.assertEquals(16, score.getSecondPoints());
        Assert.assertEquals(16, score.getMinPoints());
        Assert.assertEquals(16, score.getMaxPoints());
        Assert.assertEquals(9, score.getOverPoints());
        Assert.assertEquals(25, score.getSetPoints());
        Assert.assertEquals(78, score.getTotal());
        Assert.assertEquals(46, score.getTotal(1));
        Assert.assertEquals(32, score.getTotal(2));
        Assert.assertEquals(0, score.getTotal(3));
        Assert.assertEquals(0, score.getTotal(4));
        Assert.assertEquals(0, score.getTotal(5));
        Assert.assertEquals(1, score.getWinner(1));
        Assert.assertEquals(0, score.getWinner(2));
        Assert.assertEquals(0, score.getWinner(3));
        Assert.assertEquals(0, score.getWinner(4));
        Assert.assertEquals(0, score.getWinner(5));
    }

    @Test
    public void testVolleyballScore3()
    {
        String str = "1:2 (25:13, 24:26, 21:25, 13:14)";
        VolleyballScore score = VolleyballScore.get(str);
        Assert.assertEquals(4, score.getSet());
        Assert.assertEquals(1, score.getDiff());
        Assert.assertEquals(12, score.getDiff(1));
        Assert.assertEquals(2, score.getDiff(2));
        Assert.assertEquals(4, score.getDiff(3));
        Assert.assertEquals(1, score.getDiff(4));
        Assert.assertEquals(0, score.getDiff(5));
        Assert.assertEquals(1, score.getFirstWins());
        Assert.assertEquals(2, score.getSecondWins());
        Assert.assertEquals(13, score.getFirstPoints());
        Assert.assertEquals(14, score.getSecondPoints());
        Assert.assertEquals(13, score.getMinPoints());
        Assert.assertEquals(14, score.getMaxPoints());
        Assert.assertEquals(11, score.getOverPoints());
        Assert.assertEquals(25, score.getSetPoints());
        Assert.assertEquals(161, score.getTotal());
        Assert.assertEquals(38, score.getTotal(1));
        Assert.assertEquals(50, score.getTotal(2));
        Assert.assertEquals(46, score.getTotal(3));
        Assert.assertEquals(27, score.getTotal(4));
        Assert.assertEquals(0, score.getTotal(5));
        Assert.assertEquals(1, score.getWinner(1));
        Assert.assertEquals(2, score.getWinner(2));
        Assert.assertEquals(2, score.getWinner(3));
        Assert.assertEquals(0, score.getWinner(4));
        Assert.assertEquals(0, score.getWinner(5));
    }

    @Test
    public void testVolleyballScore4()
    {
        String str = "2:2 (25:13, 24:26, 21:25, 27:25, 7:9)";
        VolleyballScore score = VolleyballScore.get(str);
        Assert.assertEquals(5, score.getSet());
        Assert.assertEquals(2, score.getDiff());
        Assert.assertEquals(12, score.getDiff(1));
        Assert.assertEquals(2, score.getDiff(2));
        Assert.assertEquals(4, score.getDiff(3));
        Assert.assertEquals(2, score.getDiff(4));
        Assert.assertEquals(2, score.getDiff(5));
        Assert.assertEquals(2, score.getFirstWins());
        Assert.assertEquals(2, score.getSecondWins());
        Assert.assertEquals(7, score.getFirstPoints());
        Assert.assertEquals(9, score.getSecondPoints());
        Assert.assertEquals(7, score.getMinPoints());
        Assert.assertEquals(9, score.getMaxPoints());
        Assert.assertEquals(6, score.getOverPoints());
        Assert.assertEquals(15, score.getSetPoints());
        Assert.assertEquals(202, score.getTotal());
        Assert.assertEquals(38, score.getTotal(1));
        Assert.assertEquals(50, score.getTotal(2));
        Assert.assertEquals(46, score.getTotal(3));
        Assert.assertEquals(52, score.getTotal(4));
        Assert.assertEquals(16, score.getTotal(5));
        Assert.assertEquals(1, score.getWinner(1));
        Assert.assertEquals(2, score.getWinner(2));
        Assert.assertEquals(2, score.getWinner(3));
        Assert.assertEquals(1, score.getWinner(4));
        Assert.assertEquals(0, score.getWinner(5));
    }
}
