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

import java.util.ArrayList ;
import java.util.List ;


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
    private boolean isDirty = false;


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
            System.out.println("Not a valid meld.");
            }
        }   // end 1-arg constructor
<<<<<<< HEAD
=======
    
    /**
     * helper methods for Canasta rules & scoring
     */

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

>>>>>>> 9a0cb60f86337823d1235bda83089daef5556efc

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

        Hand hand = new Hand() ;

<<<<<<< HEAD
        discardPile.addCard(myStock.removeCardAt(5));
        discardPile.addToBottom(myStock.removeCardAt(5));
        discardPile.addToBottom(myStock.removeCardAt(5));
        discardPile.addToBottom(myStock.removeCardAt(5));
        discardPile.addToBottom(myStock.removeCardAt(5));
=======
        hand.addToBottom(myStock.removeCardAt(11));
        hand.addToBottom(myStock.removeCardAt(11));
        hand.addToBottom(myStock.removeCardAt(11));
>>>>>>> 9a0cb60f86337823d1235bda83089daef5556efc

         Meld meld = new Meld(hand) ;

         System.out.println(meld.revealAll());
         System.out.println(myStock.revealAll());

        }	// end main()

        /**
     * Check whether the current pile of cards is a valid Canasta meld
     * according to the official game rules.
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

    int wildCardCount = 0;
    Rank baseRank = null; // "Common" rank between all cards

    for (CardBase cb : this.cards)
        {
        Card c = (Card) cb;
        Rank r = c.rank;

<<<<<<< HEAD
        for ( final CardBase card : this.cards )
            {

            currentCard = (Card) card ;

            // If current card is joker or 2 add to the count
            if ( ( currentCard.rank == Rank.JOKER ) || ( currentCard.rank == Rank.TWO ) )
=======
        // Wild card handler
        if ( r == Rank.JOKER || r == Rank.TWO )
            {
            wildCardCount++;
            this.isDirty = true;
            if (wildCardCount >= 4)
>>>>>>> 9a0cb60f86337823d1235bda83089daef5556efc
                {
                if ( wildCardCounter == 3 )
                    {
                    return false ;
                    }
                else
                    {
                    previousCard = currentCard ;
                    wildCardCounter++ ;
                    continue ;
                    }
                }
<<<<<<< HEAD

            // Continue if previous card is null
            if ( previousCard == null )
                {
                previousCard = currentCard ;
                continue ;
                }

            // Continue if previous card is a wildcard
            if ( ( previousCard.rank == Rank.JOKER ) || ( previousCard.rank == Rank.TWO ) )
                {
                previousCard = currentCard ;
                continue ;
                }

            // At this point we've asserted that the previous card isn't a wildcard or null,
            // and that the current card is not a wildcard

            // Sets the constant card to current card if constant is null
            if ( constantCard == null )
                {
                constantCard = currentCard ;
                previousCard = currentCard ;
                continue ;
                }

            // If the constant card's rank does not match the current card's rank, not a valid meld
            if ( !constantCard.rank.equals( currentCard.rank ) )
                {
                return false ;
                }

            previousCard = currentCard ;

            }

        return true ;

        } // end validateMeld()
=======
            continue;
            }

        // Non-wild card handler

        // No melds of 3s
        if (r == Rank.THREE)
            {
            return false;
            }

        if (baseRank == null)
            {
            baseRank = r;
            } else if (r != baseRank)
            {
            // different non-wild rank → invalid
            return false;
            }
        } // end of foreach loop

    // must have at least one non-wild card
    if (baseRank == null)
        {
        return false;
        }

    // If the meld is of 7s, wild cards are not allowed in that meld
    if (baseRank == Rank.SEVEN && wildCardCount > 0)
        {
        return false;
        }

    // Checks to see if the card count is less than 3
    if (this.cardCount() < 3)
        {
        return false;
        }

    return true;

    } // end of validateMeld()


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

    /**
     * Returns a boolean if a meld is dirty
     *
     * @return boolean
     */
    public boolean isDirty()
        {

        return this.isDirty;

        }

 
>>>>>>> 9a0cb60f86337823d1235bda83089daef5556efc

        /**
     * get a snapshot of all cards currently in this meld.
     * The returned List is a copy; modifying it will not affect the meld.
     *
     * @return list of cards in this meld
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
     * is this meld a Canasta?
     * <p>
     * For our purposes, any valid meld with 7 or more cards counts.
     *
     * @return true if this meld has at least 7 cards
     */
    public boolean isCanasta()
        {

        return this.cardCount() >= 7 ;

        }   // end isCanasta()

     /**
     * count how many wild cards (2s or Jokers) are in this meld
     *
     * @return number of wild cards
     */
    public int countWildCards()
        {

        int wildCount = 0 ;

        for ( final CardBase card : this.cards )
            {
            final Card c = (Card) card ;

            if ( ( c.rank == Rank.JOKER ) || ( c.rank == Rank.TWO ) )
                {
                wildCount++ ;
                }
            }

        return wildCount ;

        }   // end countWildCards()

    }	// end class Meld