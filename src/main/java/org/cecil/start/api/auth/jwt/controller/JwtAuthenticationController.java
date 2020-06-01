package org.cecil.start.api.auth.jwt.controller;

//@RestController
//@CrossOrigin(origins = "http://localhost:3000")//react
//@Slf4j
public class JwtAuthenticationController {
//    @Value("${jwt.http-request-header}")
//    private String tokenHeader;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;
//
//    @Autowired
//    private UserDetailsService jwtUserDetailsService;
//
//    @RequestMapping(value = "${jwt.get-token-uri}", method = RequestMethod.POST)
//    public ResponseEntity<JwtTokenResponse> createAuthenticationToken(@RequestBody JwtTokenRequest tokenRequest) {
//        String username = tokenRequest.getUsername();
//        authenticate(username, tokenRequest.getPassword());
//        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
//        return ResponseEntity.ok(new JwtTokenResponse(jwtTokenUtil.generateToken(username)));
//    }
//
//    @RequestMapping(value = "${jwt.refresh-token-uri}", method = RequestMethod.GET)
//    public ResponseEntity<JwtTokenResponse> refreshAndGetAuthenticationToken(HttpServletRequest request) {
//        final String authToken = request.getHeader(tokenHeader);
//        final String token = authToken == null ? null : authToken.substring(7);
//
//        if (token != null && jwtTokenUtil.canTokenBeRefreshed(token)) {
//            String refreshedToken = jwtTokenUtil.refreshToken(token);
//            return ResponseEntity.ok(new JwtTokenResponse(refreshedToken));
//        } else {
//            return ResponseEntity.badRequest().body(null);
//        }
//    }
//
//    @ExceptionHandler({ AuthenticationException.class })
//    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
//    }
//
//    private void authenticate(String username, String password) {
//        Objects.requireNonNull(username);
//        Objects.requireNonNull(password);
//
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//        } catch (DisabledException e) {
//            throw new AuthenticationException("USER_DISABLED", e);
//        } catch (BadCredentialsException e) {
//            throw new AuthenticationException("INVALID_CREDENTIALS", e);
//        }
//    }

}
