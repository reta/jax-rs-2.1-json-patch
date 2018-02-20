/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.example;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonPatch;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.springframework.stereotype.Service;

@Service
@Path("/catalog")
public class BookRestService {
    @Inject private BookService bookService;
    @Inject private BookConverter converter;
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@Context UriInfo uriInfo, JsonObject json) {
        final Book book = converter.fromJson(json);
        bookService.add(book);
        
        return Response
            .created(uriInfo.getRequestUriBuilder().path(book.getIsbn()).build())
            .build();
    }
    
    @PATCH
    @Path("/{isbn}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void apply(@PathParam("isbn") String isbn, JsonArray operations) {
        final Book book = bookService.find(isbn).orElseThrow(NotFoundException::new);
        final JsonPatch patch = Json.createPatch(operations);
        final JsonObject result = patch.apply(converter.toJson(book));
        bookService.update(isbn, converter.fromJson(result));
    }
    
    @GET
    @Path("/{isbn}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject get(@PathParam("isbn") String isbn) {
        final Book book = bookService.find(isbn).orElseThrow(NotFoundException::new);
        return converter.toJson(book);
    }
}
