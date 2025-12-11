/* @formatter:off
 *
 * © David M Rosenberg
 *
 * Topic: List App ~ Card Game
 * 
 * Usage restrictions:
 * 
 * You may use this code for exploration, experimentation, and furthering your
 * learning for this course. You may not use this code for any other
 * assignments, in my course or elsewhere, without explicit permission, in
 * advance, from myself (and the instructor of any other course).
 * 
 * Further, you may not post (including in a public repository such as on github)
 * nor otherwise share this code with anyone other than current students in my 
 * sections of this course.
 * 
 * Violation of these usage restrictions will be considered a violation of
 * Wentworth Institute of Technology's Academic Honesty Policy.  Unauthorized posting
 * or use of this code may also be considered copyright infringement and may subject
 * the poster and/or the owners/operators of said websites to legal and/or financial
 * penalties.  My students are permitted to store this code in a private repository
 * or other private cloud-based storage.
 *
 * Do not modify or remove this notice.
 *
 * @formatter:on
 */


package edu.wit.scds.ds.lists.app.card_game.canasta.pile ;

import static edu.wit.scds.ds.lists.app.card_game.universal_base.support.Orientation.FACE_DOWN ;

import edu.wit.scds.ds.lists.app.card_game.standard_cards.card.Card ;
import edu.wit.scds.ds.lists.app.card_game.standard_cards.pile.Pile ;
import edu.wit.scds.ds.lists.app.card_game.universal_base.support.NoCardsException ;
import edu.wit.scds.ds.lists.app.card_game.universal_base.card.CardBase ;

import java.util.Collections ;
import java.util.ArrayList ;
import java.util.List ;


/**
 * Representation of a hand of cards
 * <p>
 * NOTE: You probably will modify this code
 *
 * @author Dave Rosenberg
 *
 * @version 1.0 2021-12-08 Initial implementation
 * @version 2.0 2025-03-30 track changes to {@code Pile}
 * @version 3.0 2025-06-28
 *     <ul>
 *     <li>track changes to other classes
 *     <li>switch {@code remove[Highest,Lowest]Card()} from manual loops to
 *     {@code Collections.[max,min]()}
 *     </ul>
 * 
 * @author Lynn Snow
 * 
 * @version 4.0 2025-11-03 modifications for your implementation
 */
public final class Hand extends Pile
    {

    // no additional data fields


    /*
     * constructors
     */

    /**
     * initialize hand with {@code Card}s placed face down by default
     */
    public Hand()
        {

        super( FACE_DOWN ) ;

        }	// end no-arg constructor


    /*
     * public methods
     */


    /**
     * retrieve and remove the highest value card in the hand
     *
     * @return the highest value card
     *
     * @throws NoCardsException
     *     if the hand is empty
     */
    public Card removeHighestCard() throws NoCardsException
        {

        if ( isEmpty() )
            {
            throw new NoCardsException() ;
            }

        return removeCard( Collections.max( super.cards ) ) ;

        }  // end removeHighestCard()


    /**
     * retrieve and remove the lowest value card in the hand
     *
     * @return the lowest value card
     *
     * @throws NoCardsException
     *     if the hand is empty
     */
    public Card removeLowestCard() throws NoCardsException
        {

        if ( isEmpty() )
            {
            throw new NoCardsException() ;
            }

        return removeCard( Collections.min( super.cards ) ) ;

        }  // end removeLowestCard()

    /**
     * get a snapshot of all cards currently in this hand.
     * The returned List is a copy; modifying it will not affect the hand.
     *
     * @return list of cards in this hand
     */
    public List<Card> getAllCards()
        {

        final List<Card> copy = new ArrayList<>( this.cards.size() ) ;

        for ( final CardBase c : this.cards )
            {
            copy.add( (Card) c ) ;
            }

        return copy ;

        }   // end getAllCards()


    /**
     * (optional) test driver
     *
     * @param args
     *     -unused-
     */
    public static void main( final String[] args )
        {
        // TODO Auto-generated method stub

        }	// end main()

    }	// end class Hand