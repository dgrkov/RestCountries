FROM mariadb:latest

# Set environment variables for the MariaDB root user
# Replace 'root_password' with your desired root password
ENV MYSQL_DATABASE=countries
ENV MYSQL_USER=countries
ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_PASSWORD=root

# Expose the default MariaDB port
EXPOSE 3306

# Copy any custom configuration file into the container, if needed
# For example, if you have a file named 'custom.cnf' in the same directory as your Dockerfile
# Uncomment the line below to add it to the MariaDB config
# COPY custom.cnf /etc/mysql/conf.d/

# Set the entrypoint to start MariaDB
CMD ["mysqld"]
