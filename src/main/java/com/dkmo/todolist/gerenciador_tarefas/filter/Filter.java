package com.dkmo.todolist.gerenciador_tarefas.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dkmo.todolist.gerenciador_tarefas.users.UsersRepository;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class Filter extends OncePerRequestFilter {
    @Autowired
    private UsersRepository usersRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                var servlet = request.getServletPath();
            if(servlet.startsWith("/tasks/")){
                var entrada = request.getHeader("Authorization");
                var entrada_encriptada = entrada.substring("Basic".length()).trim();
                byte [] decode = Base64.getDecoder().decode(entrada_encriptada);
                String senhaUsuario = new String(decode);
                String [] userPass = senhaUsuario.split(":");
                String usuario = userPass[0];
                String senha = userPass[1];
                var user = usersRepository.findByUsername(usuario);
                if(user==null){
                    response.sendError(401);
                }else{
                var result = BCrypt.verifyer().verify(senha.toCharArray(),user.getSenha());
                if(result.verified){
                    request.setAttribute("idUser",user.getId());
                filterChain.doFilter(request, response);
                }else{
                    response.sendError(401);
                }   
            }
            }else{
filterChain.doFilter(request, response);
            }
            }
        }