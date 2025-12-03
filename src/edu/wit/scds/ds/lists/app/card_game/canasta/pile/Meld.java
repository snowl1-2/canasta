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

import static edu.wit.scds.ds.lists.app.card_game.universal_base.support.Orientation.FACE_UP ;

import java.util.ArrayList;
import java.util.List;

import edu.wit.scds.ds.lists.app.card_game.standard_cards.card.Card;
import edu.wit.scds.ds.lists.app.card_game.standard_cards.card.Rank;
// import edu.wit.scds.ds.lists.app.card_game.standard_cards.card.Suit;
import edu.wit.scds.ds.lists.app.card_game.standard_cards.pile.Deck;
import edu.wit.scds.ds.lists.app.card_game.standard_cards.pile.Pile ;
import edu.wit.scds.ds.lists.app.card_game.universal_base.card.CardBase;
import edu.wit.scds.ds.lists.app.card_game.universal_base.support.Orientation ;

/**
 * represents a set of matching cards, typically three or more, that earn a player points and/or
 * allow them to deplete their hand
 * <p>
 * NOTE: You probably won't modify this code but you might replace it
 *
 * @author Dave Rosenberg
 *
 * @version 1.0 2025-06-28 Initial implementation (taken from {@code Field}
 * 
 * @author Lynn Snow
 * 
 * @version 2.0 2025-12-01 canasta implementation
 */
public final class Meld extends Pile
    {

    /*
     * utility constants
     */
    /** by default, cards added to a meld will be turned face up */
    private final static Orientation DEFAULT_CARD_ORIENTATION = FACE_UP ;


    /*
     * constructors
     */


    /**
     * initialize a meld
     */
    public Meld()
        {

        super( DEFAULT_CARD_ORIENTATION ) ;

        }	// end no-arg constructor


    /**
     * add the cards won to this meld
     *
     * @param cardsToadd
     *     a collection of cards
     */
    public Meld( final Pile cardsToadd )
        {

        this() ;

        super.moveCardsToBottom( cardsToadd ) ;

        if (!validateMeld())
            {
            super.removeAll() ;
            System.out.println("Not a valid canasta.") ;
            }
        }   // end 1-arg constructor
    
    /**
     * helper methods for Canasta rules & scoring
     */

    /**
     * count wild cards (Joker or 2) in this meld
     * 
     * @return number of wild cards
     */
    // public int countWildCards()
    //     {
    //     int count = 0 ;
    //     for ( final Card c : super.cards )
    //         {
    //         // final Rank r = c.getRank() ;
    //         if ( r == Rank.JOKER || r == Rank.TWO )
    //             {
    //             count++;
    //             }
    //         return count ;
    //         }
    //     }
    /**
     * is this meld a canasta (7 or more cards)
     * 
     * @return true if 7+ cards
     */
    public boolean isCanasta()
        {
        return super.cardCount() >= 7 ;
        }

    /**
     * get all cards in this meld as a List
     *
     * @return list of cards (copy)
     */
    public List<Card> getAllCards()
        {
        List<Card> result = new ArrayList<>();

        for (CardBase cb : this.cards)
            {
            result.add((Card) cb);
            }

        return result;
        } // end getAllCards()


    /*
     * testing/debugging
     */

    /**
     * (optional) test driver
     *
     * @param args
     *     -unused-
     */
    public static void main( final String[] args )
        {
        final Stock myStock = new Stock() ;     // should only accept permanent cards
        final Deck cardSource = new Deck() ;    // contains only permanent cards

        myStock.moveCardsToBottom( cardSource ) ; // I'm genuinely so pissed

        myStock.sort() ;

        System.out.println(myStock.revealAll());

        DiscardPile discardPile = new DiscardPile() ;

        discardPile.addCard(myStock.removeCardAt(5));
        discardPile.addToBottom(myStock.removeCardAt(5));
        discardPile.addToBottom(myStock.removeCardAt(5));
        discardPile.addToBottom(myStock.removeCardAt(5));
        discardPile.addToBottom(myStock.removeCardAt(5));
        //discardPile.addToBottom(myStock.removeCardAt(5));

        // Meld meld = new Meld(discardPile) ;


        }	// end main()

        /**
     * Check whether the current pile of cards is a valid Canasta meld
     * according to the project rules.
     *
     * Constraints:
     * - At least 3 cards
     * - All non-wild cards (non-JOKER, non-TWO) have the same rank
     * - At most 3 wild cards total (JOKER or TWO)
     * - No melds that contain THREE as the natural rank
     * - If the natural rank is SEVEN, wild cards are NOT allowed in that meld
     *
     * @return true if this is a valid meld
     */
    private boolean validateMeld()
    {
    if (this.cardCount() < 3)
        {
        return false;
        }

    int wildCardCount = 0;
    Rank baseRank = null;      // the natural rank all non-wilds must share

    for (CardBase cb : this.cards)
        {
        Card c = (Card) cb;
        Rank r = c.rank;   // <-- field

        // Wild cards: JOKER or TWO
        if (r == Rank.JOKER || r == Rank.TWO)
            {
            wildCardCount++;
            if (wildCardCount > 3)
                {
                return false;
                }
            continue;
            }

        // Non-wild cards:

        // No melds of 3s
        if (r == Rank.THREE)
            {
            return false;
            }

        if (baseRank == null)
            {
            baseRank = r;
            }
        else if (r != baseRank)
            {
            // different non-wild rank → invalid
            return false;
            }
        }

    // must have at least one non-wild card
    if (baseRank == null)
        {
        return false;
        }

    // no melds "of 2s" (2s are wild only)
    if (baseRank == Rank.TWO)
        {
        return false;
        }

    // If the meld is of 7s, wild cards are not allowed in that set
    if (baseRank == Rank.SEVEN && wildCardCount > 0)
        {
        return false;
        }

    return true;
    }


        /**
     * count wild cards (Joker or 2) in this meld
     *
     * @return number of wild cards
     */
    public int countWildCards()
    {
    int count = 0;

    for (CardBase cb : this.cards)
        {
        Card c = (Card) cb;
        Rank r = c.rank;   // <-- use the field, not getRank()

        if (r == Rank.JOKER || r == Rank.TWO)
            {
            count++;
            }
        }

    return count;
    }

 

    }	// end class Meld