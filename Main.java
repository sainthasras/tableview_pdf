package application;
	
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.pdfjet.A4;
import com.pdfjet.Cell;
import com.pdfjet.CoreFont;
import com.pdfjet.Font;
import com.pdfjet.PDF;
import com.pdfjet.Page;
import com.pdfjet.Table;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			TableView<Communication> tableView = new TableView<Communication>();
			
			TableColumn<Communication, String> col1 = new TableColumn<>("Country");
			col1.setCellValueFactory(new PropertyValueFactory<>("country"));
			TableColumn<Communication, String> col2 = new TableColumn<>("Population");
			col2.setCellValueFactory(new PropertyValueFactory<>("population"));
			TableColumn<Communication, String> col3 = new TableColumn<>("Elec. Prod. (kWh)");
			col3.setCellValueFactory(new PropertyValueFactory<>("electricityProduction"));
			TableColumn<Communication, String> col4 = new TableColumn<>("Elec. Cons. (kWh)");
			col4.setCellValueFactory(new PropertyValueFactory<>("electricityConsumption"));
			
			
			tableView.getColumns().addAll(col1, col2, col3, col4);
			
//			adding some sample data
			for (int i = 0; i < 150; i++) {
				tableView.getItems().add(new Communication("A certain country","460,827,146", 
						"12,997,000,000","2,822,000,000,000"));
			}
			
			Button btnSaveToPDF = new Button("Save to PDF");
			btnSaveToPDF.setOnAction(actionEvent -> {
//				we will create the pdf and page
				File out = new File("Sample.pdf");
				FileOutputStream fos;
				try {
					fos = new FileOutputStream(out);
					PDF pdf = new PDF(fos);
					Page page = new Page(pdf, A4.PORTRAIT);
					
//					font for the table heading
					Font f1 = new Font(pdf, CoreFont.HELVETICA_BOLD);
					
//					font for the pdf table data
					Font f2 = new Font(pdf, CoreFont.HELVETICA);
					
//					pdf table
					Table table = new Table();
					List<List<Cell>> tableData = new ArrayList<List<Cell>>();
					
//					table row
					List<Cell> tableRow = new ArrayList<Cell>();
					
//					let's create the headers and add them to the table row
					Cell cell = new Cell(f1, col1.getText());
					tableRow.add(cell);
					
					cell = new Cell(f1, col2.getText());
					tableRow.add(cell);
					
					cell = new Cell(f1, col3.getText());
					tableRow.add(cell);
					
					cell = new Cell(f1, col4.getText());
					tableRow.add(cell);
					
					//add row to table
					tableData.add(tableRow);
					
//					now let's get table view data, create a row for each and add row to table row
					List<Communication> items = tableView.getItems();
					
					for (Communication comm : items) {
						Cell country = new Cell(f2, comm.getCountry());
						Cell pop = new Cell(f2, comm.getPopulation());
						Cell prod = new Cell(f2, comm.getElectricityProduction());
						Cell cons = new Cell(f2, comm.getElectricityConsumption());
						
						tableRow = new ArrayList<Cell>();
						tableRow.add(country);
						tableRow.add(pop);
						tableRow.add(prod);
						tableRow.add(cons);
						
//						add row to table
						tableData.add(tableRow);
					}
					
					table.setData(tableData);
					table.setPosition(70f, 60f);
					table.setColumnWidth(0, 100f);
					table.setColumnWidth(1, 100f);
					table.setColumnWidth(2, 140f);
					table.setColumnWidth(3, 140f);
					
//					create a new page and add more table data if we get to the bottom of the current page
					while (true) {
						table.drawOn(page);
						if (!table.hasMoreData()) {
							table.resetRenderedPagesCount();
							break;
						}
						page = new Page(pdf, A4.PORTRAIT);
					}
					
					pdf.flush();
					fos.close();					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				
				System.out.println("Saved to " + out.getAbsolutePath());				
			});

			
			VBox root = new VBox(20, tableView, btnSaveToPDF);
			root.setPadding(new Insets(30));
			Scene scene = new Scene(root,500,500);
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
