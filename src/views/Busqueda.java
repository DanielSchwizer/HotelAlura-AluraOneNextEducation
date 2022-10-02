package views;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;


import controller.HuespedController;
import controller.ReservaController;

import javax.swing.JTable;
import javax.swing.JTextField;


import javax.swing.ImageIcon;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@SuppressWarnings("serial")
public class Busqueda extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable tbHuespedes;
	private JTable tbReservas;
	private DefaultTableModel modelo;
	private DefaultTableModel modeloH;
	private JLabel labelAtras;
	private HuespedController huespedController = new HuespedController();
	private ReservaController reservaController = new ReservaController();
	private JLabel labelExit;
	private boolean selectedTable = true;
	int xMouse, yMouse;

	

	/**
	 * Create the frame.
	 */
	public Busqueda() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("/imagenes/lupa2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);

		txtBuscar = new JTextField();
		txtBuscar.setBounds(536, 127, 193, 31);
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		txtBuscar.getDocument().addDocumentListener((DocumentListener) new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {

			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				if (txtBuscar.getText().length() == 0 || txtBuscar.getText().equals("")) {
					
					if (selectedTable) {
						modelo.setRowCount(1);
						getReservas();
					} else {
						modeloH.setRowCount(1);
						getHuespedes();
					}
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {

			}
		});

		JLabel lblNewLabel_4 = new JLabel("SISTEMA DE BÚSQUEDA");
		lblNewLabel_4.setForeground(new Color(12, 138, 199));
		lblNewLabel_4.setFont(new Font("Roboto Black", Font.BOLD, 24));
		lblNewLabel_4.setBounds(331, 62, 280, 42);
		contentPane.add(lblNewLabel_4);

		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.setBounds(20, 169, 865, 328);
		contentPane.add(panel);

		tbReservas = new JTable();
		tbReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbReservas.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/imagenes/reservado.png")), tbReservas,
				null);
		modelo = (DefaultTableModel) tbReservas.getModel();
		modelo.addColumn("Numero de Reserva");
		modelo.addColumn("Fecha Check In");
		modelo.addColumn("Fecha Check Out");
		modelo.addColumn("Valor");
		modelo.addColumn("Forma de Pago");
		generateReservaTable();
		getReservas();

		tbHuespedes = new JTable();
		tbHuespedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbHuespedes.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/imagenes/pessoas.png")), tbHuespedes,
				null);
		modeloH = (DefaultTableModel) tbHuespedes.getModel();
		modeloH.addColumn("Numero de Huesped");
		modeloH.addColumn("Nombre");
		modeloH.addColumn("Apellido");
		modeloH.addColumn("Fecha de Nacimiento");
		modeloH.addColumn("Nacionalidad");
		modeloH.addColumn("Telefono");
		modeloH.addColumn("Numero de Reserva");
		generateHuespedTable();
		getHuespedes();

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/Ha-100px.png")));
		lblNewLabel_2.setBounds(56, 51, 104, 107);
		contentPane.add(lblNewLabel_2);

		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedIndex = panel.getSelectedIndex();
				if (selectedIndex == 0) {
					selectedTable = true;
				} else {
					selectedTable = false;
				}
			}
		});

		JPanel header = new JPanel();
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);

			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);

		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnAtras.setBackground(Color.white);
				labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);

		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);

		JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) { // Al usuario pasar el mouse por el botón este cambiará de color
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}

			@Override
			public void mouseExited(MouseEvent e) { // Al usuario quitar el mouse por el botón este volverá al estado
													// original
				btnexit.setBackground(Color.white);
				labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(Color.WHITE);
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);

		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);

		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		separator_1_2.setBounds(539, 159, 193, 2);
		contentPane.add(separator_1_2);

		JPanel btnbuscar = new JPanel();

		btnbuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (selectedTable) {
					modelo.setRowCount(1);
					searchResultReserva(txtBuscar.getText());
				} else {
					modeloH.setRowCount(1);
					searchResultHuesped(txtBuscar.getText());
				}
			}
		});
		btnbuscar.setLayout(null);
		btnbuscar.setBackground(new Color(12, 138, 199));
		btnbuscar.setBounds(748, 125, 122, 35);
		btnbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnbuscar);

		JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.setBounds(0, 0, 122, 35);
		btnbuscar.add(lblBuscar);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(Color.WHITE);
		lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));

		JPanel btnEditar = new JPanel();
		btnEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				confirmEdit();
			}
		});
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEditar);

		JLabel lblEditar = new JLabel("EDITAR");
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);

		JPanel btnEliminar = new JPanel();
		btnEliminar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				confirmDelete();
			}
		});
		btnEliminar.setLayout(null);
		btnEliminar.setBackground(new Color(12, 138, 199));
		btnEliminar.setBounds(767, 508, 122, 35);
		btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEliminar);

		JLabel lblEliminar = new JLabel("ELIMINAR");
		lblEliminar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEliminar.setForeground(Color.WHITE);
		lblEliminar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEliminar.setBounds(0, 0, 122, 35);
		btnEliminar.add(lblEliminar);
		setResizable(false);
	}

	// Código que permite mover la ventana por la pantalla según la posición de "x"
	// y "y"
	private void headerMousePressed(java.awt.event.MouseEvent evt) {
		xMouse = evt.getX();
		yMouse = evt.getY();
	}

	private void headerMouseDragged(java.awt.event.MouseEvent evt) {
		int x = evt.getXOnScreen();
		int y = evt.getYOnScreen();
		this.setLocation(x - xMouse, y - yMouse);
	}

	private void searchResultHuesped(String apellido) {
		var search = huespedController.getHuespedApellido(apellido);
		search.forEach(huesped -> {
			modeloH.addRow(new Object[] {
					huesped.getId(),
					huesped.getNombre(),
					huesped.getApellido(),
					huesped.getFechaNacimiento(),
					huesped.getNacionalidad(),
					huesped.getTelefono(),
					huesped.getReserva_id()
			});
		});

	}

	public void searchResultReserva(String id) {
		var reservas = reservaController.getNumeroReserva(id);
		reservas.forEach(reserva -> {
			modelo.addRow(new Object[] {
					reserva.getId(),
					reserva.getFecha_entrada(),
					reserva.getFecha_salida(),
					reserva.getValor(),
					reserva.getForma_pago() });
		});
	}

	private void getHuespedes() {
		var huespedes = huespedController.list();
		huespedes.forEach(huesped -> {
			modeloH.addRow(new Object[] {
					huesped.getId(),
					huesped.getNombre(),
					huesped.getApellido(),
					huesped.getFechaNacimiento(),
					huesped.getNacionalidad(),
					huesped.getTelefono(),
					huesped.getReserva_id() });
		});
	}

	private void generateHuespedTable() {
		modeloH.addRow(new Object[] { "Numero de Huesped",
				"Nombre",
				"Apellido",
				"Fecha de Nacimiento",
				"Nacionalidad",
				"Telefono",
				"Numero de Reserva" });

	}

	private void getReservas() {
		var reservas = reservaController.list();
		reservas.forEach(reserva -> {
			modelo.addRow(new Object[] {
					reserva.getId(),
					reserva.getFecha_entrada(),
					reserva.getFecha_salida(),
					reserva.getValor(),
					reserva.getForma_pago() });
		});
	}

	private void generateReservaTable() {
		modelo.addRow(new Object[] {
				"Numero de Reserva",
				"Check In",
				"Check Out",
				"Valor",
				"Forma de Pago",
		});
	}

	public void ConfirmDialog(String mensaje, ReservaController controller, DefaultTableModel modelo, JTable tabla) {
		if (JOptionPane.showConfirmDialog(null, mensaje) == 0) {
			controller
					.delete(Integer.parseInt(tabla.getValueAt(tabla.getSelectedRow(), 0).toString()));
			modelo.removeRow(tabla.getSelectedRow());
		}
	}

	public void ConfirmDialog(String mensaje, HuespedController controller, DefaultTableModel modelo, JTable tabla) {
		if (JOptionPane.showConfirmDialog(null, mensaje) == 0) {
			controller
					.delete(Integer.parseInt(tabla.getValueAt(tabla.getSelectedRow(), 0).toString()));
			modelo.removeRow(tabla.getSelectedRow());
		}
	}

	public void confirmDelete() {
		if (selectedTable) {
			ConfirmDialog("¿Desea eliminar esta Reserva?", reservaController, modelo, tbReservas);
		} else {
			ConfirmDialog("¿Desea eliminar este huesped?", huespedController, modeloH, tbHuespedes);
		}
	}

	public void ConfirmEditDialog(String mensaje, HuespedController controller, DefaultTableModel modelo,
			JTable tabla) {
		if (JOptionPane.showConfirmDialog(null, mensaje) == 0) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date parsed;
			try {
				parsed = format.parse(tabla.getValueAt(tabla.getSelectedRow(), 3).toString());
				java.sql.Date sqlFechaNacimiento = new java.sql.Date(parsed.getTime());
				controller.update(
						tabla.getValueAt(tabla.getSelectedRow(), 1).toString(),
						tabla.getValueAt(tabla.getSelectedRow(), 2).toString(),
						sqlFechaNacimiento,
						tabla.getValueAt(tabla.getSelectedRow(), 4).toString(),
						Integer.parseInt(tabla.getValueAt(tabla.getSelectedRow(), 5).toString()),
						Integer.parseInt(tabla.getValueAt(tabla.getSelectedRow(), 0).toString()));
			} catch (ParseException e) {
				JOptionPane.showMessageDialog(null, "Fecha Incorrecta");
				e.printStackTrace();
			}

		}
	}

	public void ConfirmEditDialog(String mensaje, ReservaController controller, DefaultTableModel modelo,
			JTable tabla) {
		if (JOptionPane.showConfirmDialog(null, mensaje) == 0) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date parsedEntry;
			java.util.Date parsedOut;
			try {
				parsedEntry = format.parse(tabla.getValueAt(tabla.getSelectedRow(), 1).toString());
				parsedOut = format.parse(tabla.getValueAt(tabla.getSelectedRow(), 2).toString());
				java.sql.Date sqlFechaEntrada = new java.sql.Date(parsedEntry.getTime());
				java.sql.Date sqlFechaSalida = new java.sql.Date(parsedOut.getTime());
				controller.update(
						sqlFechaEntrada,
						sqlFechaSalida,
						Integer.parseInt(tabla.getValueAt(tabla.getSelectedRow(), 3).toString()),
						tabla.getValueAt(tabla.getSelectedRow(), 4).toString(),
						Integer.parseInt(tabla.getValueAt(tabla.getSelectedRow(), 0).toString()));
			} catch (ParseException e) {
				JOptionPane.showMessageDialog(null, "Fecha Incorrecta");
				e.printStackTrace();
			}

		}
	}

	public void confirmEdit() {
		if (selectedTable) {
			ConfirmEditDialog("¿Desea editar esta Reserva?", reservaController, modelo, tbReservas);
		} else {
			ConfirmEditDialog("¿Desea editar este huesped?", huespedController, modeloH, tbHuespedes);
		}
	}

}
