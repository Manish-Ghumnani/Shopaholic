# Warehouse Application
Steps for WareHouse Application:
1.Enter the address: http://localhost:8080/warehouse/order
2.Run the shopping cart application Parallel 

Notes:
1.Incoming Order  --> Socket Endpoint(/order)
2.Customer Order  --> Message Driven Bean (MDB) Triggers SocketEndpoint on calling onMessage()
3.Session Handler --> Singleton Class that Retrieves unique session each time.
