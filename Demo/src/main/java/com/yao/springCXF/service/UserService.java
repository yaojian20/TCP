package com.yao.springCXF.service;

import com.yao.springCXF.Response;
import com.yao.springCXF.entity.User;

import javax.jws.WebService;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.util.List;

/**
 * @author yaojian
 * @date 2020/7/5 16:28
 */

@WebService
@Path(value = "users")
public interface UserService {

    @GET
    @Path("/")
    //数据返回类型
    //@Produces(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers();


    @DELETE
    @Path("{id}")
    //@Produces(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") long id);

    @POST
    @Path("add")
    //@Produces(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(User user);

    @GET
    @Path("{id}")
    //@Produces(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("id")long id);


    @PUT
    @Path("update")
    //@Produces(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(long id);




}
