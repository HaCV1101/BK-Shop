package app.home.activity.sell;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ProductBox extends JPanel {

  private String id;
  private String name;
  private String cost;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  public boolean search(String title) {
    return this.getId().toLowerCase().contains(title.toLowerCase()) || this.getName().toLowerCase()
        .contains(title.toLowerCase());
  }

  public ProductBox(String id, String name, String cost) {
    super();
    this.id = id;
    this.name = name;
    this.cost = cost;

    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setCursor(new Cursor(Cursor.HAND_CURSOR));
    setBackground(Color.decode("#A0DAB4"));
    setBorder(BorderFactory.createLineBorder(Color.decode("#0078AA"), 3));
    setPreferredSize(new Dimension(260, 110));
    JLabel idLabel = new JLabel(id);
    idLabel.setFont(new java.awt.Font("Tahoma", Font.BOLD, 20));
    idLabel.setPreferredSize(new Dimension(-1, 30));
    idLabel.setAlignmentX(0.5f);
    add(idLabel);
    JLabel nameLabel = new JLabel(name);
    nameLabel.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 20));
    nameLabel.setAlignmentX(0.5f);
    add(nameLabel);
    JLabel costLabel = new JLabel(cost);
    costLabel.setAlignmentX(0.5f);
    costLabel.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 20));
    add(costLabel).setForeground(Color.decode("#ff0000"));
  }
}
