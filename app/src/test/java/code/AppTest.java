/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package code;

import org.checkerframework.checker.units.qual.A;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class AppTest {

    @Test
    public void testCountMinUpdate(){

        //Create heavy hitter object
        NewHeavyHitters hh = new NewHeavyHitters(5);

        //Initialize 32 HH count mins
        hh.cms = new CountMinSketch[32];
        for(int i = 0; i<32; i++){
             hh.cms[i] = new CountMinSketch(20, 15, 1000003);
        }

        //Adds 7 items for each count min and initializes them to zero
        for(CountMinSketch cm : hh.cms){
            for(int i = 0; i<7; i++){
                cm.add(i,0);
            }
        }

        //Update heavy hitter
        hh.update(7, 1);

        //Layer 0, item 7 has been incremented
        assertThat(hh.cms[0].estimateCount(7), is(1));

        //Layer 1, item 3 has been incremented
        assertThat(hh.cms[1].estimateCount(3), is(1));

        //Layer 2, item 1 has been incremented
        assertThat(hh.cms[2].estimateCount(1), is(1));

        //Layer 3, item 0 has been incremented
        assertThat(hh.cms[3].estimateCount(0), is(1));


        //Testing for false positives
        assertThat(hh.cms[0].estimateCount(6), is(not(equalTo(1))));
        assertThat(hh.cms[0].estimateCount(5), is(not(equalTo(1))));
        assertThat(hh.cms[1].estimateCount(5), is(not(equalTo(1))));
        assertThat(hh.cms[2].estimateCount(3), is(not(equalTo(1))));
    }


    @Test
    public void testCountMinQuery(){

        //Create heavy hitter object
        NewHeavyHitters hh = new NewHeavyHitters(3);

        //Initialize 3 HH count mins
        hh.cms = new CountMinSketch[32];
        for(int i = 0; i<32; i++){
            hh.cms[i] = new CountMinSketch(20, 15, 1000003);
        }

        hh.update(3, 2);
        hh.update(8, 4);
        hh.update(12, 2);
        hh.update(12, 5);

        //Query heavy hitters
        ArrayList<Integer> heavyHittersList = new ArrayList<>();
        hh.query(heavyHittersList);


        for(CountMinSketch cm : hh.cms){
            System.out.println("-- New Count min --");
            for(int i = 0; i<32; i++){
                System.out.println(cm.estimateCount(i));
            }
        }

        for(int i : heavyHittersList){
            System.out.println("I am a heavy hitter -->" + i);
        }





    }


}
