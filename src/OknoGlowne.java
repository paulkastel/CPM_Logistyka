
import java.io.*;
import java.util.*;
import javax.swing.*;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kastel
 */
public class OknoGlowne extends javax.swing.JFrame
{

	/**
	 * Wyznacza Sciezke krytyczna w grafie
	 */
	public void SciezkaKrytyczna()
	{
		//Lista bedaca sciezka krytyczna
		List<Zdarzenie> criticalpath = new ArrayList<>();

		//Uruchom jezeli jest podany graf wpp wyswietl komunikat
		if (!Strzalki.isEmpty())
		{

			//Zadaj kadzemu elementowi czas poczatkowy (jest ok) i wyswietl do konsoli
			for (int i = 0; i < Strzalki.size(); i++)
			{
				//SPRAWDZAJ CZY NOWY NIE JEST MNIEJSZY I POTEM PRZY END_TIME NIE JEST WIEKSZY CZY COS
				if (Strzalki.get(i).nastepne.start_time < Strzalki.get(i).poprzednie.start_time + Strzalki.get(i).Czas)
				{
					Strzalki.get(i).nastepne.start_time = Strzalki.get(i).poprzednie.start_time + Strzalki.get(i).Czas;
					Strzalki.get(i).nastepne.end_time = Integer.MAX_VALUE;
				}

				//System.out.println(Strzalki.get(i).poprzednie.Nazwa + " do " + Strzalki.get(i).nastepne.Nazwa);
				//System.out.println(Strzalki.get(i).poprzednie.start_time + "+" + Strzalki.get(i).Czas + "=" + Strzalki.get(i).nastepne.start_time);

				//x.nastepne.start_time = x.poprzednie.start_time + x.Czas;
				//System.out.println(x.poprzednie.Nazwa + " do " + x.nastepne.Nazwa);
				//System.out.println(x.poprzednie.start_time + "+" + x.Czas + "=" + x.nastepne.start_time);
			}

			//Ostatni element start_time=end_time
			Strzalki.get(Strzalki.size() - 1).nastepne.end_time = Strzalki.get(Strzalki.size() - 1).nastepne.start_time;

			//Zadaj kazdemu elementowi czas koncowy od tylu do poczatku
			for (int i = Strzalki.size() - 1; i > 0; i--)
			{
				if (Strzalki.get(i - 1).nastepne.Nazwa.equals(Strzalki.get(i).nastepne.Nazwa))
				{
					Strzalki.get(i - 1).nastepne.end_time = Strzalki.get(i).nastepne.end_time;
				}

				//Strzalki.get(i).poprzednie.end_time = Strzalki.get(i).poprzednie.start_time; 
				if (Strzalki.get(i).poprzednie.end_time > (Strzalki.get(i).nastepne.end_time - Strzalki.get(i).Czas))
				{
					Strzalki.get(i).poprzednie.end_time = Strzalki.get(i).nastepne.end_time - Strzalki.get(i).Czas;
				}

				Strzalki.get(i).nastepne.luz = Strzalki.get(i).nastepne.end_time - Strzalki.get(i).nastepne.start_time;
				//Strzalki.get(i).poprzednie.end_time = Strzalki.get(i).nastepne.end_time - Strzalki.get(i).Czas;
				//System.out.println(Strzalki.get(i).nastepne.Nazwa + " do " + Strzalki.get(i).poprzednie.Nazwa);
				//System.out.println(Strzalki.get(i).nastepne.end_time + "-" + Strzalki.get(i).Czas + "=" + Strzalki.get(i).poprzednie.end_time);
			}
			Strzalki.get(0).poprzednie.luz = 0;
			Strzalki.get(0).poprzednie.end_time = 0;

			//Przydzielam zdarzeniom w liscie wydarzen ich czasy poczatkowe
			for (int i = 0; i < Wydarzenia.size() - 1; i++)
			{
				int licz = 0;
				for (int j = 0; j < Strzalki.size(); j++)
				{
					if (Strzalki.get(j).poprzednie.Nazwa.equals(Wydarzenia.get(i).Nazwa))
					{
						break;
					}
					licz++;
				}
				Wydarzenia.get(i).start_time = Strzalki.get(licz).poprzednie.start_time;
				Wydarzenia.get(i).end_time = Strzalki.get(licz).poprzednie.end_time;
				Wydarzenia.get(i).luz = Strzalki.get(licz).poprzednie.luz;
				//System.err.println(Wydarzenia.get(i).Nazwa + ": " + Wydarzenia.get(i).start_time + " " + Wydarzenia.get(i).end_time + " " + Wydarzenia.get(i).luz);
			}
			Wydarzenia.get(Wydarzenia.size() - 1).start_time = Strzalki.get(Strzalki.size() - 1).nastepne.start_time;
			Wydarzenia.get(Wydarzenia.size() - 1).end_time = Strzalki.get(Strzalki.size() - 1).nastepne.end_time;
			Wydarzenia.get(Wydarzenia.size() - 1).luz = Strzalki.get(Strzalki.size() - 1).nastepne.luz;

			//Wylicz luzy w kazdym Zdarzeniu i jezeli luz ==0 to dodaj do listy ze sciezka krytyczna
			for (Zdarzenie x : Wydarzenia)
			{
				if (x.luz == 0)
				{
					criticalpath.add(x);
				}
			}
			String KriticPath = "";
			for (Zdarzenie x : criticalpath)
			{
				KriticPath += " -> "+x.Nazwa;
			}
			JOptionPane.showMessageDialog(null, KriticPath, "SciezkaKrytyczna", JOptionPane.PLAIN_MESSAGE);
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Nie wykryto żadnych czynnosci!", "Error!", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Dane globalne
	 */
	DefaultListModel listModel = new DefaultListModel();	//Do wyswietlania w okienku
	List<Zdarzenie> Wydarzenia = new ArrayList<>();			//Lista Zdarzen
	List<Czynnosc> Strzalki = new ArrayList<>();			//Lista czynnosci laczaca zdarzenia
	File plik = new File("Graf.txt");						//Plik z ktorego zczytuje dane

	/**
	 * Creates new form OknoGlowne
	 */
	public OknoGlowne()
	{
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jFrame1 = new javax.swing.JFrame();
        WybZdarzeniaDial = new javax.swing.JDialog();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jLabel8 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        NazwaZdarzeniaField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lastZdarzenieComboBox = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ZdarzeniaLista = new javax.swing.JList(listModel);
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        CzasCzynnosciField = new javax.swing.JFormattedTextField();
        DodajZdarzeniebtn = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        nextZdarzenieComboBox = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        DodajCzynnoscbtn = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        WczytajzPliku = new javax.swing.JMenuItem();
        WyznaczSciezke = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        WybZdarzeniaDial.setResizable(false);

        jScrollPane2.setViewportView(jList1);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Wybierz zdarzenie poczatkowe");

        jButton1.setText("OK");

        javax.swing.GroupLayout WybZdarzeniaDialLayout = new javax.swing.GroupLayout(WybZdarzeniaDial.getContentPane());
        WybZdarzeniaDial.getContentPane().setLayout(WybZdarzeniaDialLayout);
        WybZdarzeniaDialLayout.setHorizontalGroup(
            WybZdarzeniaDialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(WybZdarzeniaDialLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(WybZdarzeniaDialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        WybZdarzeniaDialLayout.setVerticalGroup(
            WybZdarzeniaDialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(WybZdarzeniaDialLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Logistyka CPM");
        setResizable(false);

        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Dodaj nowe zdarzenie");

        jLabel2.setLabelFor(CzasCzynnosciField);
        jLabel2.setText("Czas Czynnosci");

        jLabel3.setLabelFor(lastZdarzenieComboBox);
        jLabel3.setText("Poprzednie Zdarzenie");

        ZdarzeniaLista.setToolTipText("Lista Zdarzeń w projekcie");
        ZdarzeniaLista.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        ZdarzeniaLista.setDragEnabled(true);
        ZdarzeniaLista.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                ZdarzeniaListaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(ZdarzeniaLista);

        jLabel4.setLabelFor(NazwaZdarzeniaField);
        jLabel4.setText("Nazwa Zdarzenia");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setLabelFor(ZdarzeniaLista);
        jLabel5.setText("Lista czynności");
        jLabel5.setVerifyInputWhenFocusTarget(false);

        CzasCzynnosciField.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyTyped(java.awt.event.KeyEvent evt)
            {
                CzasCzynnosciFieldKeyTyped(evt);
            }
        });

        DodajZdarzeniebtn.setText("Dodaj Zdarzenie");
        DodajZdarzeniebtn.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                DodajZdarzeniebtnActionPerformed(evt);
            }
        });

        jLabel6.setLabelFor(lastZdarzenieComboBox);
        jLabel6.setText("Nastepne Zdarzenie");

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Dodaj nową czynność");

        DodajCzynnoscbtn.setText("Dodaj Czynnosc");
        DodajCzynnoscbtn.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                DodajCzynnoscbtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(NazwaZdarzeniaField))
                    .addComponent(jSeparator1)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(DodajZdarzeniebtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(CzasCzynnosciField, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lastZdarzenieComboBox, 0, 137, Short.MAX_VALUE)
                            .addComponent(nextZdarzenieComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(DodajCzynnoscbtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(NazwaZdarzeniaField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(DodajZdarzeniebtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(lastZdarzenieComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(nextZdarzenieComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CzasCzynnosciField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(DodajCzynnoscbtn))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        CzasCzynnosciField.getAccessibleContext().setAccessibleName("");

        jMenu1.setText("Plik");

        WczytajzPliku.setText("Wczytaj plik");
        WczytajzPliku.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                WczytajzPlikuActionPerformed(evt);
            }
        });
        jMenu1.add(WczytajzPliku);

        WyznaczSciezke.setText("Ścieżka Krytyczna");
        WyznaczSciezke.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                WyznaczSciezkeActionPerformed(evt);
            }
        });
        jMenu1.add(WyznaczSciezke);

        jMenuItem1.setText("Zamknij");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(new JSeparator());
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Pomoc");

        jMenuItem2.setText("O Programie");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

	/**
	 * O Autorach XD
	 */
    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItem2ActionPerformed
    {//GEN-HEADEREND:event_jMenuItem2ActionPerformed
		JOptionPane.showMessageDialog(null, "Metoda CPM\nAutorzy:\nPaweł Kastelik\nWiktoria Krzeminska\nKraków 2016", "O Programie!", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

	/**
	 * Po wcisnieciu przycisku dodaje nowe Zdarzenie od listy Zdarzen i dodaje
	 * do comboboxa
	 *
	 */
    private void DodajZdarzeniebtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_DodajZdarzeniebtnActionPerformed
    {//GEN-HEADEREND:event_DodajZdarzeniebtnActionPerformed
		//Pole nie moze byc puste zeby zadzialac
		if (NazwaZdarzeniaField.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(null, "Musisz podac nazwe Zdarzenia!", "Error!", JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			//jak zadziala to dodaj zdarzenie do listy i do combobox i wyczysc textfield
			Zdarzenie q = new Zdarzenie(NazwaZdarzeniaField.getText());
			NazwaZdarzeniaField.setText(null);

			lastZdarzenieComboBox.addItem(new ComboItem(q.Nazwa, q));
			nextZdarzenieComboBox.addItem(new ComboItem(q.Nazwa, q));

			//jezeli to pierwsze wydarzenie to zdefiniuj wszystkie parametry
			if (Wydarzenia.isEmpty())
			{
				q.start_time = 0;
				q.end_time = 0;
				q.luz = 0;
			}
			//Dodaj do listy wydarzen
			Wydarzenia.add(q);
			//JOptionPane.showMessageDialog(null, "Wydarzenie zostało dodane!", "Komunikat", JOptionPane.INFORMATION_MESSAGE);
		}
    }//GEN-LAST:event_DodajZdarzeniebtnActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItem1ActionPerformed
    {//GEN-HEADEREND:event_jMenuItem1ActionPerformed
		//Zamknij program kurwa
		dispose();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

	/**
	 * Do tego textfieldu mozesz podac tylko liczby
	 *
	 * @param evt
	 */
    private void CzasCzynnosciFieldKeyTyped(java.awt.event.KeyEvent evt)//GEN-FIRST:event_CzasCzynnosciFieldKeyTyped
    {//GEN-HEADEREND:event_CzasCzynnosciFieldKeyTyped

		char enter = evt.getKeyChar();
		if (!(Character.isDigit(enter)))
		{
			evt.consume();
		}
    }//GEN-LAST:event_CzasCzynnosciFieldKeyTyped

	/**
	 * Po podwojnym kliknieciu w liste pokazuje wiecej szczegolow
	 *
	 * @param evt
	 */
    private void ZdarzeniaListaMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_ZdarzeniaListaMouseClicked
    {//GEN-HEADEREND:event_ZdarzeniaListaMouseClicked
		if (evt.getClickCount() == 2)
		{
			Object it = ZdarzeniaLista.getSelectedValue();
			Czynnosc q = ((ComboItem) it).getCzynnosc();
			JOptionPane.showMessageDialog(null, "Poprzednie wydarzenie: " + q.poprzednie.Nazwa + "\nNastepne zdarzenie: " + q.nastepne.Nazwa + "\nCzas czynnosci: " + q.Czas, "Szczegolowe informacje", JOptionPane.INFORMATION_MESSAGE);
		}
    }//GEN-LAST:event_ZdarzeniaListaMouseClicked

	/**
	 * Ma dodawac Czynnosci ale narazie kurwa musze zadzialac na pliku ja
	 * pierdole :/
	 *
	 * @param evt
	 */
    private void DodajCzynnoscbtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_DodajCzynnoscbtnActionPerformed
    {//GEN-HEADEREND:event_DodajCzynnoscbtnActionPerformed
		//pola nie moga byc puste aby dodac poprawnie czynnosc bo wpp wyswietli odpowiedni error messagebox
		if (!(lastZdarzenieComboBox.getSelectedItem() == null || nextZdarzenieComboBox.getSelectedItem() == null || CzasCzynnosciField.getText().isEmpty()))
		{
			boolean isOK = true;
			Object o1 = lastZdarzenieComboBox.getSelectedItem();
			Zdarzenie z1 = ((ComboItem) o1).getZdarzenie();
			Object o2 = nextZdarzenieComboBox.getSelectedItem();
			Zdarzenie z2 = ((ComboItem) o2).getZdarzenie();
			if (!(z1 == z2))
			{
				for (Czynnosc x : Strzalki)
				{
					if (x.nastepne == z1 && x.poprzednie == z2)
					{
						isOK = false;
						break;
					}
				}
				if (isOK)
				{
					Czynnosc tmp = new Czynnosc(Integer.parseInt(CzasCzynnosciField.getText()), z1, z2);
					listModel.addElement(new ComboItem(tmp.poprzednie.Nazwa + " -> " + tmp.nastepne.Nazwa, tmp));
					Strzalki.add(tmp);
					CzasCzynnosciField.setText(null);
					//JOptionPane.showMessageDialog(null, "Czynnosc zostala dodana!", "Komunikat", JOptionPane.INFORMATION_MESSAGE);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Nie mozesz utworzyc polaczenia dwustronnego", "Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Nie mozesz polaczyc dwoch takich samych zdarzen", "Error!", JOptionPane.ERROR_MESSAGE);
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Nie wypelniles ktoregos z pol!", "Error!", JOptionPane.ERROR_MESSAGE);
		}
    }//GEN-LAST:event_DodajCzynnoscbtnActionPerformed
	/**
	 * Uruchamia funkcje wyznaczajaca sciezke krytyczna. Osobna funkcja opisana
	 *
	 * @param evt
	 */
    private void WyznaczSciezkeActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_WyznaczSciezkeActionPerformed
    {//GEN-HEADEREND:event_WyznaczSciezkeActionPerformed
		SciezkaKrytyczna();
    }//GEN-LAST:event_WyznaczSciezkeActionPerformed

	/**
	 * wczytuje dane z sformatowanego pliku: nazwa poprzedniego eventu, nazwa
	 * nastepnego eventu, czas trwania
	 *
	 * @param evt
	 */
    private void WczytajzPlikuActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_WczytajzPlikuActionPerformed
    {//GEN-HEADEREND:event_WczytajzPlikuActionPerformed
		//Sproboj odczytac plik a potem
		try
		{
			//Wyczysc wszystko co bylo do tej pory.
			lastZdarzenieComboBox.removeAllItems();
			nextZdarzenieComboBox.removeAllItems();
			Wydarzenia.clear();
			Strzalki.clear();
			listModel.clear();

			//Dane pomocnicze
			String prv = null, nex = null;	//nazwy poprzedniego i nastepnego zdarzenia
			int tmpczs = 0;					//czas trwania wydarzenia
			Scanner in = new Scanner(plik);	//scanner do czytania pliku

			int zwieksz = 0;
			while (in.hasNext())			//dopoki end of file
			{
				boolean ist = false;
				prv = in.next();			//pobierz dane
				nex = in.next();
				tmpczs = in.nextInt();

				//jak lista pusta to dodaj i zdefiniuj pierwsze dwa eventy
				if (Wydarzenia.isEmpty())
				{
					Wydarzenia.add(new Zdarzenie(prv));
					Wydarzenia.add(new Zdarzenie(nex));

					Wydarzenia.get(zwieksz).start_time = 0;
					Wydarzenia.get(zwieksz).end_time = Integer.MAX_VALUE;
					Wydarzenia.get(zwieksz).luz = 0;
				}
				else
				{
					for (Zdarzenie q : Wydarzenia)
					{
						if (q.Nazwa.equals(prv))
						{
							ist = true;
							break;
						}
					}
					if (ist == false)
					{
						Wydarzenia.add(new Zdarzenie(prv));
					}
				}

				//jezeli lista pusta to dodaj pierwsze wydarzenie z Wydarzen i nowe nastepne
				if (Strzalki.isEmpty())
				{
					Strzalki.add(new Czynnosc(tmpczs, Wydarzenia.get(Wydarzenia.size() - 2), Wydarzenia.get(Wydarzenia.size() - 1)));
				}
				//wpp
				else
				{
					//jezeli aktualny poprzednik jest rowny poprzedniemu nastepnikowi
					if (prv.equals(Strzalki.get(Strzalki.size() - 1).nastepne.Nazwa))
					{
						Strzalki.add(new Czynnosc(tmpczs, Strzalki.get(Strzalki.size() - 1).nastepne, new Zdarzenie(nex)));
					}
					//a jezeli aktualny poprzednik nie jest rowny to znajdz ostatni element ktory nim byl!
					else
					{
						int licz = 0;
						for (Czynnosc x : Strzalki)
						{
							if (prv.equals(x.nastepne.Nazwa))
							{
								break;
							}
							licz++;
						}
						Strzalki.add(new Czynnosc(tmpczs, Strzalki.get(licz).nastepne, new Zdarzenie(nex)));
					}
				}
				zwieksz++;
			}

			Wydarzenia.add(new Zdarzenie(nex));	//dodaj ostatnie wydarzenie do listy

			//for wypelnia wszystkie itemy w gui
			for (int i = 0; i < Strzalki.size(); i++)
			{
				if (i < Strzalki.size() - 1)
				{
					lastZdarzenieComboBox.addItem(new ComboItem(Wydarzenia.get(i).Nazwa, Wydarzenia.get(i)));
					nextZdarzenieComboBox.addItem(new ComboItem(Wydarzenia.get(i).Nazwa, Wydarzenia.get(i)));
				}
				listModel.addElement(new ComboItem(Strzalki.get(i).poprzednie.Nazwa + " -> " + Strzalki.get(i).nastepne.Nazwa, Strzalki.get(i)));
			}

		} catch (FileNotFoundException ex)
		{
			//Logger.getLogger(OknoGlowne.class.getName()).log(Level.SEVERE, null, ex);
		}
    }//GEN-LAST:event_WczytajzPlikuActionPerformed

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[])
	{

		/* Set the Nimbus look and feel */
		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
		 * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
		 */
		try
		{
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
			{
				if ("Nimbus".equals(info.getName()))
				{
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex)
		{
			java.util.logging.Logger.getLogger(OknoGlowne.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex)
		{
			java.util.logging.Logger.getLogger(OknoGlowne.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex)
		{
			java.util.logging.Logger.getLogger(OknoGlowne.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex)
		{
			java.util.logging.Logger.getLogger(OknoGlowne.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				new OknoGlowne().setVisible(true);
			}
		});
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField CzasCzynnosciField;
    private javax.swing.JButton DodajCzynnoscbtn;
    private javax.swing.JButton DodajZdarzeniebtn;
    private javax.swing.JTextField NazwaZdarzeniaField;
    private javax.swing.JMenuItem WczytajzPliku;
    private javax.swing.JDialog WybZdarzeniaDial;
    private javax.swing.JMenuItem WyznaczSciezke;
    private javax.swing.JList ZdarzeniaLista;
    private javax.swing.JButton jButton1;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JList jList1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JComboBox lastZdarzenieComboBox;
    private javax.swing.JComboBox nextZdarzenieComboBox;
    // End of variables declaration//GEN-END:variables

}
