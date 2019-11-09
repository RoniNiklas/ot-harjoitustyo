/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Roni
 */
public class KassapääteTest {
    private Kassapaate kassapaate;
    private Maksukortti kortti;
    @Before
    public void setUp() {
        kassapaate = new Kassapaate();
        kortti = new Maksukortti(400);
    }
    @Test
    public void konstruktoriToimii() {
        assertEquals(100000, kassapaate.kassassaRahaa());
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());
    }
    @Test
    public void voiMyydaMaukkaan() {
        assertEquals(100, kassapaate.syoMaukkaasti(500));
        assertEquals(100400, kassapaate.kassassaRahaa());
        assertEquals(1, kassapaate.maukkaitaLounaitaMyyty());
    }
    @Test
    public void voiMyydaEdullisen() {
        assertEquals(100, kassapaate.syoEdullisesti(340));
        assertEquals(100240, kassapaate.kassassaRahaa());
        assertEquals(1, kassapaate.edullisiaLounaitaMyyty());
    }
    @Test
    public void voiMyydaMaukkaanKortti() {
        assertTrue(kassapaate.syoMaukkaasti(kortti));
        assertEquals(0, kortti.saldo());
        assertEquals(1, kassapaate.maukkaitaLounaitaMyyty());
    }
    @Test
    public void voiMyydaEdullisenKortti() {
        assertTrue(kassapaate.syoEdullisesti(kortti));
        assertEquals(160, kortti.saldo());
        assertEquals(1, kassapaate.edullisiaLounaitaMyyty());
    }
    @Test
    public void eiVoiMyydaKoyhalle() {
        assertEquals(100, kassapaate.syoEdullisesti(100));
        assertEquals(100, kassapaate.syoMaukkaasti(100));
        assertEquals(100000, kassapaate.kassassaRahaa());
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
    }
    @Test
    public void eiVoiMyydaKoyhalleKortti() {
        assertTrue(kassapaate.syoEdullisesti(kortti));
        assertEquals(160, kortti.saldo());
        assertEquals(1, kassapaate.edullisiaLounaitaMyyty());
        assertFalse(kassapaate.syoMaukkaasti(kortti));
        assertFalse(kassapaate.syoEdullisesti(kortti));
        assertEquals(160, kortti.saldo());
        assertEquals(1, kassapaate.edullisiaLounaitaMyyty());
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
    }
    @Test
    public void voiLadataRahaa() {
        kassapaate.lataaRahaaKortille(kortti, 1000);
        assertEquals(1400, kortti.saldo());
        assertEquals(101000, kassapaate.kassassaRahaa());
    }
    @Test
    public void eiVoiLadataMiinusRahaa() {
        kassapaate.lataaRahaaKortille(kortti, -1000);
        assertEquals(400, kortti.saldo());
        assertEquals(100000, kassapaate.kassassaRahaa());
    }
}
