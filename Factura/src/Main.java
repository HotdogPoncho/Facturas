import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.text.DecimalFormat;

public class Main {
    public static void main(String[] args) {
        try {
            double totalSinImpuesto = 0.0;
            double impuestoTotal = 0.0;
            double totalConImpuesto = 0.0;
            // Crear una fábrica de constructores de documentos
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Leer el archivo XML y crear un documento DOM
            //Document document = builder.parse("src\\Factura_A_8767.xml");
            Document document = builder.parse("src\\Factura.xml");
            //Document document = builder.parse("src\\Factura_A_8789.xml");

            // Obtener el elemento raíz
            Element rootElement = document.getDocumentElement();

            // Obtener datos del comprobante
            String version = rootElement.getAttribute("Version");
            String serie = rootElement.getAttribute("Serie");
            String folio = rootElement.getAttribute("Folio");
            String fecha = rootElement.getAttribute("Fecha");
            String subTotal = rootElement.getAttribute("SubTotal");
            String total = rootElement.getAttribute("Total");

            System.out.println("Comprobante:");
            System.out.println("Versión: " + version);
            System.out.println("Serie: " + serie);
            System.out.println("Folio: " + folio);
            System.out.println("Fecha: " + fecha);
            System.out.println("Subtotal: " + subTotal);
            System.out.println("Total: " + total);
            System.out.println();

            // Obtener datos del emisor
            Element emisorElement = (Element) rootElement.getElementsByTagName("cfdi:Emisor").item(0);
            String rfcEmisor = emisorElement.getAttribute("Rfc");
            String nombreEmisor = emisorElement.getAttribute("Nombre");
            String regimenFiscalEmisor = emisorElement.getAttribute("RegimenFiscal");

            System.out.println("Emisor:");
            System.out.println("RFC: " + rfcEmisor);
            System.out.println("Nombre: " + nombreEmisor);
            System.out.println("Régimen fiscal: " + regimenFiscalEmisor);
            System.out.println();

            // Obtener datos del receptor
            Element receptorElement = (Element) rootElement.getElementsByTagName("cfdi:Receptor").item(0);
            String rfcReceptor = receptorElement.getAttribute("Rfc");
            String nombreReceptor = receptorElement.getAttribute("Nombre");
            String domicilioFiscalReceptor = receptorElement.getAttribute("DomicilioFiscalReceptor");
            String regimenFiscalReceptor = receptorElement.getAttribute("RegimenFiscalReceptor");
            String usoCfdi = receptorElement.getAttribute("UsoCFDI");

            System.out.println("Receptor:");
            System.out.println("RFC: " + rfcReceptor);
            System.out.println("Nombre: " + nombreReceptor);
            System.out.println("Domicilio fiscal receptor: " + domicilioFiscalReceptor);
            System.out.println("Régimen fiscal receptor: " + regimenFiscalReceptor);
            System.out.println("Uso CFDI: " + usoCfdi);
            System.out.println();

            // Obtener datos de los conceptos
            NodeList conceptos = rootElement.getElementsByTagName("cfdi:Concepto");

            for (int i = 0; i < conceptos.getLength(); i++) {
                Element conceptoElement = (Element) conceptos.item(i);
                String claveProdServ = conceptoElement.getAttribute("ClaveProdServ");
                String cantidad = conceptoElement.getAttribute("Cantidad");
                String descripcion = conceptoElement.getAttribute("Descripcion");
                String valorUnitario = conceptoElement.getAttribute("ValorUnitario");
                String importe = conceptoElement.getAttribute("Importe");

                System.out.println("Concepto " + (i + 1) + ":");
                System.out.println("Clave del producto/servicio: " + claveProdServ);
                System.out.println("Cantidad: " + cantidad);
                System.out.println("Descripción: " + descripcion);
                System.out.println("Valor unitario: " + valorUnitario);
                System.out.println("Importe: " + importe);
                System.out.println();
                impuestoTotal += (Double.valueOf(importe)*.16);
                totalSinImpuesto += Double.valueOf(importe) ;

                // Obtener datos de los impuestos trasladados
                Element impuestosElement = (Element) conceptoElement.getElementsByTagName("cfdi:Impuestos").item(0);
                Element trasladosElement = (Element) impuestosElement.getElementsByTagName("cfdi:Traslados").item(0);
                Element trasladoElement = (Element) trasladosElement.getElementsByTagName("cfdi:Traslado").item(0);
                String base = trasladoElement.getAttribute("Base");
                String impuesto = trasladoElement.getAttribute("Impuesto");
                String tipoFactor = trasladoElement.getAttribute("TipoFactor");
                String tasaOCuota = trasladoElement.getAttribute("TasaOCuota");
                String importeImpuesto = trasladoElement.getAttribute("Importe");

                System.out.println("Impuestos trasladados - Concepto " + (i + 1) + ":");
                System.out.println("Base: " + base);
                System.out.println("Impuesto: " + impuesto);
                System.out.println("Tipo de factor: " + tipoFactor);
                System.out.println("Tasa o cuota: " + tasaOCuota);
                System.out.println("Importe: " + importeImpuesto);
                System.out.println();
            }

            // Obtener datos de los impuestos totales
            /*Element impuestosTotalesElement = (Element) rootElement.getElementsByTagName("cfdi:Impuestos").item(0);
            Element trasladosTotalesElement = (Element) impuestosTotalesElement.getElementsByTagName("cfdi:Traslados").item(0);
            Element trasladoTotalesElement = (Element) trasladosTotalesElement.getElementsByTagName("cfdi:Traslado").item(0);
            String totalImpuestosTrasladados = trasladoTotalesElement.getAttribute("Importe");

            System.out.println("Impuestos:");
            System.out.println("Total impuestos trasladados: " + totalImpuestosTrasladados);
            System.out.println();*/

            System.out.println("Importe total sin impuesto:" + totalSinImpuesto);
            System.out.println("Importe total del impuesto:" + impuestoTotal);
            DecimalFormat df = new DecimalFormat("#.##"); // Define el formato con dos decimales
            String importeTotal = df.format(totalSinImpuesto+impuestoTotal);
            System.out.println("Importa total: $" + importeTotal);




        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
