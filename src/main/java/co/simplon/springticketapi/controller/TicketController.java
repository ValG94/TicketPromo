package co.simplon.springticketapi.controller;

import co.simplon.springticketapi.dao.TicketDao;
import co.simplon.springticketapi.model.Ticket;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

@RequestMapping("/api/ticket")
@RestController
public class TicketController {

    private JdbcTemplate jdbcTemplate;

    private final TicketDao ticketDao;

    public TicketController(TicketDao ticketDao, JdbcTemplate jdbcTemplate) {
        this.ticketDao = ticketDao;
        this.jdbcTemplate = jdbcTemplate;
    }
    //Récupération de tous les tickets émis
    @GetMapping
    public List<Ticket> getAllTickets() throws SQLException {
        //return ticketDao.getAll(); permet de faire toutes les instructions ci-dessous

        Connection dbConnection = jdbcTemplate.getDataSource().getConnection();

        //Préparation de ma requête
        List<Ticket> ticketsList = new ArrayList<>();
        String selectReq = "SELECT * FROM ticket";

        try (PreparedStatement statement = dbConnection.prepareStatement(selectReq)) {
            //Execution de la requête
            ResultSet set = statement.executeQuery();
            // Tant que j'ai des tickets, je les ajoute dans ma BDD

            while (set.next()) {
                ticketsList.add(new Ticket(set.getLong("number_ticket"),
                        set.getDate("date").toLocalDate(),
                        set.getString("description"),
                        set.getString("student"),
                        set.getBoolean("resolved"),
                        set.getString("promo_name")));
            }
            set.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return ticketsList;
    }

    //Récupération des tickets émis grâce à leur ID
    @GetMapping("/{id}")
    public Ticket getTicketByID(@PathVariable Long id) throws SQLException {
        // Je me connecte à la BDD
        Connection dbConnection = jdbcTemplate.getDataSource().getConnection();

        // Je prépare ma requête
        Ticket ticket = null;
        String selectReq = "SELECT * FROM tiket where id = ?";

        try (PreparedStatement statement = dbConnection.prepareStatement(selectReq)) {
            // J'exécute ma requête
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();

            // Tant que j'ai des tickets, je les ajoute à la suite des autres dans ma BDD
            if (set.next()) {
                ticket = new Ticket(set.getLong("id"),
                        set.getDate("date").toLocalDate(),
                        set.getString("description"),
                        set.getString("student"),
                        set.getBoolean("resolved"),
                        set.getString("promo_Name"));
            }
            set.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        // Je retourne la liste des tickets
        return ticketDao.get(id);
    }

    //Ajout d'un nouveau ticket avec POST
    @PostMapping
    public Ticket postTicket(@RequestBody Ticket ticket) throws SQLException {
        // Je me connecte à la BDD
        Connection dbConnection = jdbcTemplate.getDataSource().getConnection();
        String insertCmd = "INSERT INTO ticket (student, description, resolved, promo_name) VALUES(?, ?, ?, ?);";
        try (PreparedStatement stmt = dbConnection.prepareStatement(insertCmd, Statement.RETURN_GENERATED_KEYS)) {
            //Je prépare ma requête
            stmt.setString(1, ticket.getStudent());
            stmt.setString(2, ticket.getDescription());
            stmt.setBoolean(3, ticket.getResolved());
            stmt.setString(4, ticket.getPromoName());

            //Exécute la requête
            stmt.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return ticket;
        //System.out.println(ticket);
        //return ticketDao.post(ticket);
    }

    //Mise à jour des tickets une fois qu'ils sont traités
    @PutMapping
    public Ticket putTicket(@RequestBody Ticket ticket) throws SQLException {
        //Je me connecte à la BDD
        Connection dbConnection = jdbcTemplate.getDataSource().getConnection();
        String deleteCmd = "UPDATE ticket SET resolved = ? where number_ticket = ?";
        try (PreparedStatement delcmd = dbConnection.prepareStatement(deleteCmd, Statement.RETURN_GENERATED_KEYS)) {
            //Je prépare ma requête pour effacer les tickets traités
            delcmd.setBoolean(1, ticket.getResolved());
            delcmd.setLong(2, ticket.getId());
            //delcmd.setString(2, ticket.getStudent());

            // Exécute la requête
            delcmd.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return ticket;
    }
}
