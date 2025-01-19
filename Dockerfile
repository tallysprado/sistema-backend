FROM jboss/keycloak:11.0.3

RUN sed -i -E "s/(<staticMaxAge>)2592000(<\/staticMaxAge>)/\1\-1\2/" /opt/jboss/keycloak/standalone/configuration/standalone.xml
RUN sed -i -E "s/(<cacheThemes>)true(<\/cacheThemes>)/\1false\2/" /opt/jboss/keycloak/standalone/configuration/standalone.xml
RUN sed -i -E "s/(<cacheTemplates>)true(<\/cacheTemplates>)/\1false\2/" /opt/jboss/keycloak/standalone/configuration/standalone.xml

# Make the realm configuration available for import
COPY imports/realm-export.json /opt/keycloak/import/realm-export.json

# Import the realm and user
RUN /opt/keycloak/bin/kc.sh import --file/opt/keycloak/import/realm-export.json

# The Keycloak server is configured to listen on port 8080
EXPOSE 9081
EXPOSE 8443

# Import the realm on start-up
CMD ["start-dev"]