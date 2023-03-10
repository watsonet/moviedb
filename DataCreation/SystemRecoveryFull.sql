USE [master]
GO
/****** Object:  Database [MediaDB]    Script Date: 2/16/2023 5:04:17 PM ******/
CREATE DATABASE [MediaDB]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'MediaDB_dat', FILENAME = N'D:\Database\MSSQL15.MSSQLSERVER\MSSQL\DATA\MediaDB.mdf' , SIZE = 51200KB , MAXSIZE = 10485760KB , FILEGROWTH = 15%)
 LOG ON 
( NAME = N'MediaDB_log', FILENAME = N'D:\Database\MSSQL15.MSSQLSERVER\MSSQL\DATA\MediaDB.ldf' , SIZE = 557312KB , MAXSIZE = 5242880KB , FILEGROWTH = 15%)
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [MediaDB] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [MediaDB].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [MediaDB] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [MediaDB] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [MediaDB] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [MediaDB] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [MediaDB] SET ARITHABORT OFF 
GO
ALTER DATABASE [MediaDB] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [MediaDB] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [MediaDB] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [MediaDB] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [MediaDB] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [MediaDB] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [MediaDB] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [MediaDB] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [MediaDB] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [MediaDB] SET  ENABLE_BROKER 
GO
ALTER DATABASE [MediaDB] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [MediaDB] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [MediaDB] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [MediaDB] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [MediaDB] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [MediaDB] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [MediaDB] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [MediaDB] SET RECOVERY FULL 
GO
ALTER DATABASE [MediaDB] SET  MULTI_USER 
GO
ALTER DATABASE [MediaDB] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [MediaDB] SET DB_CHAINING OFF 
GO
ALTER DATABASE [MediaDB] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [MediaDB] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [MediaDB] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [MediaDB] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'MediaDB', N'ON'
GO
ALTER DATABASE [MediaDB] SET QUERY_STORE = OFF
GO
USE [MediaDB]
GO
/****** Object:  User [MediaDBAppAcc]    Script Date: 2/16/2023 5:04:17 PM ******/
CREATE USER [MediaDBAppAcc] FOR LOGIN [MediaDBAppAcc] WITH DEFAULT_SCHEMA=[dbo]
GO
/****** Object:  User [jonescl1]    Script Date: 2/16/2023 5:04:17 PM ******/
CREATE USER [jonescl1] FOR LOGIN [jonescl1] WITH DEFAULT_SCHEMA=[dbo]
GO
/****** Object:  User [huangr2]    Script Date: 2/16/2023 5:04:17 PM ******/
CREATE USER [huangr2] FOR LOGIN [huangr2] WITH DEFAULT_SCHEMA=[dbo]
GO
ALTER ROLE [db_datareader] ADD MEMBER [MediaDBAppAcc]
GO
ALTER ROLE [db_datawriter] ADD MEMBER [MediaDBAppAcc]
GO
ALTER ROLE [db_owner] ADD MEMBER [jonescl1]
GO
ALTER ROLE [db_owner] ADD MEMBER [huangr2]
GO
/****** Object:  Table [dbo].[ActedIn]    Script Date: 2/16/2023 5:04:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ActedIn](
	[ActorID] [int] NOT NULL,
	[MediaID] [bigint] NOT NULL,
 CONSTRAINT [PK_ActedIn] PRIMARY KEY CLUSTERED 
(
	[ActorID] ASC,
	[MediaID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Actor]    Script Date: 2/16/2023 5:04:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Actor](
	[ID] [int] NOT NULL,
	[Name] [nvarchar](40) NOT NULL,
	[Gender] [nvarchar](10) NOT NULL,
 CONSTRAINT [PK__Actor__3214EC27B417DE79] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Hosts]    Script Date: 2/16/2023 5:04:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Hosts](
	[MediaID] [bigint] NOT NULL,
	[ServiceID] [int] NOT NULL,
 CONSTRAINT [PK_Hosts] PRIMARY KEY CLUSTERED 
(
	[MediaID] ASC,
	[ServiceID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Media]    Script Date: 2/16/2023 5:04:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Media](
	[ID] [bigint] NOT NULL,
	[Title] [nvarchar](50) NULL,
	[Rating] [float] NULL,
	[ReleaseDate] [date] NULL,
	[IsAdult] [int] NULL,
 CONSTRAINT [PK__Media__3214EC270C4BD17C] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Movie]    Script Date: 2/16/2023 5:04:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Movie](
	[MediaID] [bigint] NULL,
	[Runtime] [int] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Show]    Script Date: 2/16/2023 5:04:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Show](
	[MediaID] [bigint] NOT NULL,
	[NumSeasons] [int] NULL,
	[NumEpisodes] [int] NULL,
	[LastEpDate] [date] NULL,
 CONSTRAINT [PK_Show] PRIMARY KEY CLUSTERED 
(
	[MediaID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[StreamingService]    Script Date: 2/16/2023 5:04:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[StreamingService](
	[ID] [int] NOT NULL,
	[SName] [nvarchar](50) NULL,
 CONSTRAINT [PK__Streamin__3214EC275446D05D] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Subscribed]    Script Date: 2/16/2023 5:04:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Subscribed](
	[ServiceID] [int] NULL,
	[Username] [nvarchar](50) NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[UserProfile]    Script Date: 2/16/2023 5:04:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[UserProfile](
	[Username] [nvarchar](50) NOT NULL,
	[Password] [varchar](30) NULL,
	[PasswordSalt] [varchar](30) NULL,
 CONSTRAINT [PK__UserProf__536C85E5EE65BB3F] PRIMARY KEY CLUSTERED 
(
	[Username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Watched]    Script Date: 2/16/2023 5:04:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Watched](
	[MediaID] [bigint] NULL,
	[Username] [nvarchar](50) NULL
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[ActedIn]  WITH CHECK ADD  CONSTRAINT [FK_ActedIn_Actor] FOREIGN KEY([ActorID])
REFERENCES [dbo].[Actor] ([ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[ActedIn] CHECK CONSTRAINT [FK_ActedIn_Actor]
GO
ALTER TABLE [dbo].[ActedIn]  WITH CHECK ADD  CONSTRAINT [FK_ActedIn_Media] FOREIGN KEY([MediaID])
REFERENCES [dbo].[Media] ([ID])
GO
ALTER TABLE [dbo].[ActedIn] CHECK CONSTRAINT [FK_ActedIn_Media]
GO
ALTER TABLE [dbo].[Hosts]  WITH CHECK ADD  CONSTRAINT [FK_Hosts_Media] FOREIGN KEY([MediaID])
REFERENCES [dbo].[Media] ([ID])
GO
ALTER TABLE [dbo].[Hosts] CHECK CONSTRAINT [FK_Hosts_Media]
GO
ALTER TABLE [dbo].[Hosts]  WITH CHECK ADD  CONSTRAINT [FK_Hosts_StreamingService] FOREIGN KEY([ServiceID])
REFERENCES [dbo].[StreamingService] ([ID])
GO
ALTER TABLE [dbo].[Hosts] CHECK CONSTRAINT [FK_Hosts_StreamingService]
GO
ALTER TABLE [dbo].[Movie]  WITH CHECK ADD  CONSTRAINT [FK__Movie__MediaID__22751F6C] FOREIGN KEY([MediaID])
REFERENCES [dbo].[Media] ([ID])
GO
ALTER TABLE [dbo].[Movie] CHECK CONSTRAINT [FK__Movie__MediaID__22751F6C]
GO
ALTER TABLE [dbo].[Show]  WITH CHECK ADD  CONSTRAINT [FK__Show__MediaID__236943A5] FOREIGN KEY([MediaID])
REFERENCES [dbo].[Media] ([ID])
GO
ALTER TABLE [dbo].[Show] CHECK CONSTRAINT [FK__Show__MediaID__236943A5]
GO
ALTER TABLE [dbo].[Subscribed]  WITH CHECK ADD  CONSTRAINT [FK__Subscribe__Usern__37A5467C] FOREIGN KEY([Username])
REFERENCES [dbo].[UserProfile] ([Username])
GO
ALTER TABLE [dbo].[Subscribed] CHECK CONSTRAINT [FK__Subscribe__Usern__37A5467C]
GO
ALTER TABLE [dbo].[Subscribed]  WITH CHECK ADD  CONSTRAINT [FK_Subscribed_StreamingService] FOREIGN KEY([ServiceID])
REFERENCES [dbo].[StreamingService] ([ID])
GO
ALTER TABLE [dbo].[Subscribed] CHECK CONSTRAINT [FK_Subscribed_StreamingService]
GO
ALTER TABLE [dbo].[UserProfile]  WITH CHECK ADD  CONSTRAINT [FK_UserProfile_UserProfile] FOREIGN KEY([Username])
REFERENCES [dbo].[UserProfile] ([Username])
GO
ALTER TABLE [dbo].[UserProfile] CHECK CONSTRAINT [FK_UserProfile_UserProfile]
GO
ALTER TABLE [dbo].[UserProfile]  WITH CHECK ADD  CONSTRAINT [FK_UserProfile_UserProfile1] FOREIGN KEY([Username])
REFERENCES [dbo].[UserProfile] ([Username])
GO
ALTER TABLE [dbo].[UserProfile] CHECK CONSTRAINT [FK_UserProfile_UserProfile1]
GO
ALTER TABLE [dbo].[Watched]  WITH CHECK ADD  CONSTRAINT [FK__Watched__MediaID__1DB06A4F] FOREIGN KEY([MediaID])
REFERENCES [dbo].[Media] ([ID])
GO
ALTER TABLE [dbo].[Watched] CHECK CONSTRAINT [FK__Watched__MediaID__1DB06A4F]
GO
ALTER TABLE [dbo].[Watched]  WITH CHECK ADD  CONSTRAINT [FK__Watched__Usernam__34C8D9D1] FOREIGN KEY([Username])
REFERENCES [dbo].[UserProfile] ([Username])
GO
ALTER TABLE [dbo].[Watched] CHECK CONSTRAINT [FK__Watched__Usernam__34C8D9D1]
GO
ALTER TABLE [dbo].[Actor]  WITH CHECK ADD  CONSTRAINT [CK__Actor__ID__0F624AF8] CHECK  (([ID]>=(0)))
GO
ALTER TABLE [dbo].[Actor] CHECK CONSTRAINT [CK__Actor__ID__0F624AF8]
GO
ALTER TABLE [dbo].[Actor]  WITH CHECK ADD  CONSTRAINT [CK__Actor__ID__10566F31] CHECK  (([ID]>=(0)))
GO
ALTER TABLE [dbo].[Actor] CHECK CONSTRAINT [CK__Actor__ID__10566F31]
GO
ALTER TABLE [dbo].[Media]  WITH CHECK ADD  CONSTRAINT [CK__Media__ID__14270015] CHECK  (([ID]>=(0)))
GO
ALTER TABLE [dbo].[Media] CHECK CONSTRAINT [CK__Media__ID__14270015]
GO
ALTER TABLE [dbo].[Media]  WITH CHECK ADD  CONSTRAINT [CK__Media__Rating__160F4887] CHECK  (([Rating]>=(0)))
GO
ALTER TABLE [dbo].[Media] CHECK CONSTRAINT [CK__Media__Rating__160F4887]
GO
ALTER TABLE [dbo].[Media]  WITH CHECK ADD  CONSTRAINT [CK__Media__Rating__17036CC0] CHECK  (([Rating]<=(10)))
GO
ALTER TABLE [dbo].[Media] CHECK CONSTRAINT [CK__Media__Rating__17036CC0]
GO
ALTER TABLE [dbo].[Media]  WITH CHECK ADD  CONSTRAINT [CK__Media__ReleaseDa__19DFD96B] CHECK  (([ReleaseDate]<=getdate()))
GO
ALTER TABLE [dbo].[Media] CHECK CONSTRAINT [CK__Media__ReleaseDa__19DFD96B]
GO
ALTER TABLE [dbo].[Movie]  WITH CHECK ADD  CONSTRAINT [CK__Movie__Runtime__412EB0B6] CHECK  (([Runtime]>=(0)))
GO
ALTER TABLE [dbo].[Movie] CHECK CONSTRAINT [CK__Movie__Runtime__412EB0B6]
GO
ALTER TABLE [dbo].[Show]  WITH CHECK ADD  CONSTRAINT [CK__Show__LastEpDate__3A4CA8FD] CHECK  (([LastEpDate]<=getdate()))
GO
ALTER TABLE [dbo].[Show] CHECK CONSTRAINT [CK__Show__LastEpDate__3A4CA8FD]
GO
ALTER TABLE [dbo].[Show]  WITH CHECK ADD  CONSTRAINT [CK__Show__LastEpDate__3E52440B] CHECK  (([LastEpDate]<=getdate()))
GO
ALTER TABLE [dbo].[Show] CHECK CONSTRAINT [CK__Show__LastEpDate__3E52440B]
GO
ALTER TABLE [dbo].[Show]  WITH CHECK ADD  CONSTRAINT [CK__Show__NumEpisode__3B40CD36] CHECK  (([NumEpisodes]>=(0)))
GO
ALTER TABLE [dbo].[Show] CHECK CONSTRAINT [CK__Show__NumEpisode__3B40CD36]
GO
ALTER TABLE [dbo].[Show]  WITH CHECK ADD  CONSTRAINT [CK__Show__NumEpisode__3D5E1FD2] CHECK  (([NumEpisodes]>=(0)))
GO
ALTER TABLE [dbo].[Show] CHECK CONSTRAINT [CK__Show__NumEpisode__3D5E1FD2]
GO
ALTER TABLE [dbo].[Show]  WITH CHECK ADD  CONSTRAINT [CK__Show__NumSeasons__3C34F16F] CHECK  (([NumSeasons]>=(0)))
GO
ALTER TABLE [dbo].[Show] CHECK CONSTRAINT [CK__Show__NumSeasons__3C34F16F]
GO
ALTER TABLE [dbo].[Show]  WITH CHECK ADD  CONSTRAINT [CK__Show__NumSeasons__3C69FB99] CHECK  (([NumSeasons]>=(0)))
GO
ALTER TABLE [dbo].[Show] CHECK CONSTRAINT [CK__Show__NumSeasons__3C69FB99]
GO
ALTER TABLE [dbo].[StreamingService]  WITH CHECK ADD  CONSTRAINT [CK__StreamingSer__ID__2645B050] CHECK  (([ID]>=(0)))
GO
ALTER TABLE [dbo].[StreamingService] CHECK CONSTRAINT [CK__StreamingSer__ID__2645B050]
GO
/****** Object:  StoredProcedure [dbo].[addActedIn]    Script Date: 2/16/2023 5:04:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[addActedIn]
(@AID int, @MID int)

AS 

IF @AID IS NULL
BEGIN 
	RAISERROR('Actor ID cannot be null', 1, 1)
	RETURN 1
END

IF @MID IS NULL
BEGIN 
	RAISERROR('Media ID cannot be null', 1, 1)
	RETURN 2
END

IF NOT EXISTS(SELECT ID FROM Actor WHERE ID = @AID)
BEGIN 
	RAISERROR('ID does not exist in Actor table', 1, 1)
	RETURN 3
END

IF NOT EXISTS(SELECT ID FROM Media WHERE ID = @MID)
BEGIN 
	RAISERROR('ID does not exist in Media table', 1, 1)
	RETURN 4
END
IF EXISTS(SELECT * FROM ActedIn WHERE (@AID = ActorID AND @MID = MediaID))
BEGIN
	RAISERROR('Service-Media pair must be unique',1,1)
	RETURN 5
END
INSERT INTO [ActedIn]
(ActorID, MediaID)
VALUES (@AID, @MID)
GO
/****** Object:  StoredProcedure [dbo].[addActor]    Script Date: 2/16/2023 5:04:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[addActor]
(@ID int, @Name nvarchar(40), @Gender nvarchar(10))

AS 

IF EXISTS(SELECT ID FROM Actor WHERE ID = @ID)
BEGIN 
	RAISERROR('ID already exists', 1, 1)
	RETURN 1
END

IF @ID IS NULL
BEGIN 
	RAISERROR('ID can not be null', 1, 1)
	RETURN 2
END

IF @ID < 0
BEGIN 
	RAISERROR('Invalid ID value', 1, 1)
	RETURN 3
END

IF @Name IS NULL
BEGIN 
	RAISERROR('Name can not be null', 1, 1)
	RETURN 4
END


INSERT INTO Actor
(ID, [Name], Gender)
VALUES (@ID, @Name, @Gender)
GO
/****** Object:  StoredProcedure [dbo].[addHosts]    Script Date: 2/16/2023 5:04:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[addHosts]
(@SID int, @MID int)

AS 

IF @SID IS NULL
BEGIN 
	RAISERROR('Service ID cannot be null', 1, 1)
	RETURN 1
END

IF @MID IS NULL
BEGIN 
	RAISERROR('Media ID cannot be null', 1, 1)
	RETURN 2
END

IF NOT EXISTS(SELECT ID FROM StreamingService WHERE ID = @SID)
BEGIN 
	RAISERROR('ID does not exist in Streaming Service table', 1, 1)
	RETURN 3
END

IF NOT EXISTS(SELECT ID FROM Media WHERE ID = @MID)
BEGIN 
	RAISERROR('ID does not exist in Media table', 1, 1)
	RETURN 4
END
IF EXISTS(SELECT * FROM HOSTS WHERE (@SID = ServiceID AND @MID = MediaID))
BEGIN
	RAISERROR('Service-Media pair must be unique',1,1)
	RETURN 5
END
INSERT INTO [Hosts]
(ServiceID, MediaID)
VALUES (@SID, @MID)
GO
/****** Object:  StoredProcedure [dbo].[addMedia]    Script Date: 2/16/2023 5:04:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[addMedia]
(@ID int, @Title nvarchar(50), @Rating float, @ReleaseDate date, @isAdult int)

AS 

IF EXISTS(SELECT ID FROM Media WHERE ID = @ID)
BEGIN 
	RAISERROR('ID already exists', 1, 1)
	RETURN 1
END

IF @ID IS NULL
BEGIN 
	RAISERROR('ID cannot be null', 1, 1)
	RETURN 2
END

IF @ID < 0
BEGIN 
	RAISERROR('ID cannot be negative', 1, 1)
	RETURN 3
END

IF @Title IS NULL OR @Title = ''
BEGIN 
	RAISERROR('Title cannot be null', 1, 1)
	RETURN 4
END

IF @Rating IS NULL
BEGIN 
	RAISERROR('Rating cannot be null', 1, 1)
	RETURN 5
END

IF @ReleaseDate IS NULL
BEGIN 
	RAISERROR('Release Date cannot be null', 1, 1)
	RETURN 6
END

IF @isAdult <> 0 AND @isAdult <> 1
BEGIN 
	RAISERROR('Invalid value for isAdult', 1, 1)
	RETURN 7
END

IF @Rating > 10 or @Rating < 0
BEGIN 
	RAISERROR('Rating must be between 0 and 5', 1, 1)
	RETURN 8
END

INSERT INTO Media
(ID, Title, Rating, ReleaseDate, isAdult)
VALUES (@ID, @Title, @Rating, @ReleaseDate, @isAdult)
GO
/****** Object:  StoredProcedure [dbo].[addMovie]    Script Date: 2/16/2023 5:04:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[addMovie]
(@MediaID int, @Runtime int)

AS 

IF @MediaID IS NULL
BEGIN 
	RAISERROR('MediaID can not be null', 1, 1)
	RETURN 1
END

IF NOT EXISTS(SELECT ID FROM Media WHERE ID = @MediaID)
BEGIN 
	RAISERROR('ID does not exist in Media table', 1, 1)
	RETURN 2
END

IF EXISTS(SELECT MediaID FROM Movie WHERE MediaID = @MediaID)
BEGIN 
	RAISERROR('ID already exists in Movie table', 1, 1)
	RETURN 3
END

IF @Runtime IS NULL
BEGIN 
	RAISERROR('Runtime can not be null', 1, 1)
	RETURN 4
END

INSERT INTO Movie
(MediaID, Runtime)
VALUES (@MediaID, @Runtime)
GO
/****** Object:  StoredProcedure [dbo].[addService]    Script Date: 2/16/2023 5:04:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[addService]
(@ID int, @SName varchar(50))
AS 

IF EXISTS(SELECT ID FROM StreamingService WHERE ID = @ID)
BEGIN 
	RAISERROR('ID already exists', 1, 1)
	RETURN 1
END

IF @ID IS NULL
BEGIN 
	RAISERROR('ID cannot be null', 1, 1)
	RETURN 2
END

IF @ID < 0
BEGIN 
	RAISERROR('ID cannot be negative', 1, 1)
	RETURN 3
END

IF @SName IS NULL
BEGIN 
	RAISERROR('Streaming Service name can not be null', 1, 1)
	RETURN 4
END

INSERT INTO [StreamingService]
(ID, SName)
VALUES (@ID, @SName)
GO
/****** Object:  StoredProcedure [dbo].[addShow]    Script Date: 2/16/2023 5:04:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[addShow]
(@MediaID bigint, @NumSeasons int, @NumEpisodes int, @LastEpDate date)

AS 

IF @MediaID IS NULL
BEGIN 
	RAISERROR('MediaID can not be null', 1, 1)
	RETURN 1
END

IF NOT EXISTS(SELECT ID FROM Media WHERE ID = @MediaID)
BEGIN 
	RAISERROR('ID does not exist in Media table', 1, 1)
	RETURN 2
END

IF EXISTS(SELECT MediaID FROM Show WHERE MediaID = @MediaID)
BEGIN 
	RAISERROR('ID already exists in Show table', 1, 1)
	RETURN 3
END

IF @NumSeasons IS NULL
BEGIN 
	RAISERROR('NumSeasons can not be null', 1, 1)
	RETURN 4
END

IF @NumEpisodes IS NULL
BEGIN 
	RAISERROR('NumEpisodes can not be null', 1, 1)
	RETURN 5
END

IF @LastEpDate IS NULL
BEGIN 
	RAISERROR('Last episode date can not be null', 1, 1)
	RETURN 6
END

IF @NumSeasons < 0
BEGIN 
	RAISERROR('NumEpisodes can not be null', 1, 1)
	RETURN 7
END

IF @NumEpisodes < 0
BEGIN 
	RAISERROR('NumSeasons can not be negative', 1, 1)
	RETURN 8
END

INSERT INTO [Show]
(MediaID, NumSeasons, NumEpisodes, LastEpDate)
VALUES (@MediaID, @NumSeasons, @NumEpisodes, @LastEpDate)
GO
/****** Object:  StoredProcedure [dbo].[addSubscribed]    Script Date: 2/16/2023 5:04:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[addSubscribed]
(@Username nvarchar(20), @SID int)

AS 

IF @Username IS NULL
BEGIN 
	RAISERROR('Username cannot be null', 1, 1)
	RETURN 1
END

IF @SID IS NULL
BEGIN 
	RAISERROR('Service ID cannot be null', 1, 1)
	RETURN 2
END

IF NOT EXISTS(SELECT Username FROM UserProfile WHERE Username = @Username)
BEGIN 
	RAISERROR('Username does not exist in User Profiles table', 1, 1)
	RETURN 3
END

IF NOT EXISTS(SELECT ID FROM StreamingService WHERE ID = @SID)
BEGIN 
	RAISERROR('ID does not exist in Streaming Service table', 1, 1)
	RETURN 4
END

IF EXISTS(SELECT * FROM Subscribed WHERE Username = @Username AND ServiceID= @SID)
BEGIN 
	RAISERROR('User and Subscription combination already exists', 1, 1)
	RETURN 5
END

INSERT INTO [Subscribed]
(Username, ServiceID)
VALUES (@Username, @SID)
GO
/****** Object:  StoredProcedure [dbo].[addUser]    Script Date: 2/16/2023 5:04:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[addUser]
(@Username varchar(20), @Password varchar(30), @PasswordSalt varchar(30))

AS 

IF @Username IS NULL
BEGIN 
	RAISERROR('Username can not be null', 1, 1)
	RETURN 1
END

IF @Password IS NULL
BEGIN 
	RAISERROR('Password can not be null', 1, 1)
	RETURN 2
END

IF EXISTS(SELECT Username FROM UserProfile WHERE Username = @Username)
BEGIN 
	RAISERROR('Username already exists in UserProfile table', 1, 1)
	RETURN 3
END

INSERT INTO UserProfile
(Username, [Password], PasswordSalt)
VALUES (@Username, @Password, @PasswordSalt)

RETURN 0
GO
/****** Object:  StoredProcedure [dbo].[addWatched]    Script Date: 2/16/2023 5:04:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[addWatched]
(@Username nvarchar(20), @MID int)

AS 

IF @Username IS NULL
BEGIN 
	RAISERROR('Username cannot be null', 1, 1)
	RETURN 1
END

IF @MID IS NULL
BEGIN 
	RAISERROR('Media ID cannot be null', 1, 1)
	RETURN 2
END

IF NOT EXISTS(SELECT Username FROM UserProfile WHERE Username = @Username)
BEGIN 
	RAISERROR('Username does not exist in User Profiles table', 1, 1)
	RETURN 3
END

IF NOT EXISTS(SELECT ID FROM Media WHERE ID = @MID)
BEGIN 
	RAISERROR('ID does not exist in Media table', 1, 1)
	RETURN 4
END

IF EXISTS(SELECT Username, MediaID FROM Watched WHERE Username = @Username AND MediaID = @MID)
BEGIN 
	RAISERROR('User and Media combination already exists', 1, 1)
	RETURN 3
END

INSERT INTO [Watched]
(Username, MediaID)
VALUES (@Username, @MID)
GO
/****** Object:  StoredProcedure [dbo].[checkUser]    Script Date: 2/16/2023 5:04:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[checkUser]
(@UName nvarchar(50))

AS 
if EXISTS(SELECT Username FROM UserProfile WHERE Username = @Uname)
begin
SELECT Password, PasswordSalt FROM UserProfile WHERE Username = @Uname
return 0
end

return 1
GO
/****** Object:  StoredProcedure [dbo].[delActedIn]    Script Date: 2/16/2023 5:04:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[delActedIn]
(@AID int, @MID int)

AS
-- Validates parameters
IF @AID IS NULL
BEGIN 
	RAISERROR('Actor ID cannot be null', 1, 1)
	RETURN 1
END

IF @MID IS NULL
BEGIN 
	RAISERROR('Media ID cannot be null', 1, 1)
	RETURN 2
END

IF NOT EXISTS(SELECT ActorID, MediaID FROM ActedIn WHERE ActorID = @AID AND MediaID = @MID)
BEGIN 
	RAISERROR('Actor and Media combination does not exist', 1, 1)
	RETURN 3
END


DELETE [ActedIn] 
WHERE (ActorID = @AID AND MediaID = @MID)


GO
/****** Object:  StoredProcedure [dbo].[delActor]    Script Date: 2/16/2023 5:04:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[delActor]
--(@IsAdult bit, @Gender varchar(10), @Name varchar(30), @ID int)
(@ID int)

AS
-- Validates parameters
IF (@ID is null)
BEGIN 
	RAISERROR('ID can not be null', 1, 1)
	RETURN 1
END
IF NOT EXISTS (SELECT ID FROM Actor WHERE @ID = ID)
BEGIN 
	RAISERROR('ID does not exist', 1, 1)
	RETURN 2
END

DELETE [Actor] 
WHERE (ID = @ID)


GO
/****** Object:  StoredProcedure [dbo].[delHosts]    Script Date: 2/16/2023 5:04:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[delHosts]
(@SID int, @MID int)

AS
-- Validates parameters
IF @SID IS NULL
BEGIN 
	RAISERROR('Service ID cannot be null', 1, 1)
	RETURN 1
END

IF @MID IS NULL
BEGIN 
	RAISERROR('Media ID cannot be null', 1, 1)
	RETURN 2
END

IF NOT EXISTS(SELECT ServiceID, MediaID FROM Hosts WHERE ServiceID = @SID AND MediaID = @MID)
BEGIN 
	RAISERROR('Service and Media combination does not exist', 1, 1)
	RETURN 3
END


DELETE [Hosts] 
WHERE (ServiceID = @SID AND MediaID = @MID)


GO
/****** Object:  StoredProcedure [dbo].[delMedia]    Script Date: 2/16/2023 5:04:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[delMedia]
(@ID int)

AS
-- Validates parameters
IF (@ID is null)
BEGIN 
	RAISERROR('ID can not be null', 1, 1)
	RETURN 1
END
IF NOT EXISTS (SELECT ID FROM Media WHERE @ID = ID)
BEGIN 
	RAISERROR('ID does not exist', 1, 1)
	RETURN 2
END

DELETE [Media] 
WHERE (ID = @ID)


GO
/****** Object:  StoredProcedure [dbo].[delMovie]    Script Date: 2/16/2023 5:04:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[delMovie]
(@MediaID int)

AS
-- Validates parameters
IF (@MediaID is null)
BEGIN 
	RAISERROR('ID can not be null', 1, 1)
	RETURN 1
END
IF NOT EXISTS (SELECT MediaID FROM Movie WHERE @MediaID = @MediaID)
BEGIN 
	RAISERROR('ID does not exist', 1, 1)
	RETURN 2
END

DELETE [Movie] 
WHERE (MediaID = @MediaID)


GO
/****** Object:  StoredProcedure [dbo].[delService]    Script Date: 2/16/2023 5:04:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[delService]
(@ID int)

AS
-- Validates parameters
IF (@ID is null)
BEGIN 
	RAISERROR('ID can not be null', 1, 1)
	RETURN 1
END
IF NOT EXISTS (SELECT ID FROM StreamingService WHERE @ID = ID)
BEGIN 
	RAISERROR('ID does not exist', 1, 1)
	RETURN 2
END

DELETE [StreamingService] 
WHERE (ID = @ID)


GO
/****** Object:  StoredProcedure [dbo].[delShow]    Script Date: 2/16/2023 5:04:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[delShow]
(@MediaID int)

AS
-- Validates parameters
IF (@MediaID is null)
BEGIN 
	RAISERROR('ID can not be null', 1, 1)
	RETURN 1
END
IF NOT EXISTS (SELECT MediaID FROM Show WHERE @MediaID = @MediaID)
BEGIN 
	RAISERROR('ID does not exist', 1, 1)
	RETURN 2
END

DELETE [Show] 
WHERE (MediaID = @MediaID)


GO
/****** Object:  StoredProcedure [dbo].[delSubscribed]    Script Date: 2/16/2023 5:04:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[delSubscribed]
(@Username nvarchar(20), @SID int)

AS
-- Validates parameters
IF @Username IS NULL
BEGIN 
	RAISERROR('Username cannot be null', 1, 1)
	RETURN 1
END

IF @SID IS NULL
BEGIN 
	RAISERROR('Service ID cannot be null', 1, 1)
	RETURN 2
END

IF NOT EXISTS(SELECT Username, ServiceID FROM Subscribed WHERE Username = @Username AND ServiceID = @SID)
BEGIN 
	RAISERROR('User and Service combination does not exist', 1, 1)
	RETURN 3
END


DELETE [Subscribed] 
WHERE (Username = @Username AND ServiceID = @SID)


GO
/****** Object:  StoredProcedure [dbo].[delUser]    Script Date: 2/16/2023 5:04:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[delUser]
(@Username varchar(20))

AS
-- Validates parameters
IF (@Username is null)
BEGIN 
	RAISERROR('Username can not be null', 1, 1)
	RETURN 1
END
IF NOT EXISTS (SELECT Username FROM UserProfile WHERE @Username = Username)
BEGIN 
	RAISERROR('Username does not exist', 1, 1)
	RETURN 2
END

DELETE [UserProfile] 
WHERE (Username = @Username)


GO
/****** Object:  StoredProcedure [dbo].[delWatched]    Script Date: 2/16/2023 5:04:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO

CREATE PROCEDURE [dbo].[delWatched]
(@Username nvarchar(20), @MID int)

AS
-- Validates parameters
IF @Username IS NULL
BEGIN 
	RAISERROR('Username cannot be null', 1, 1)
	RETURN 1
END

IF @MID IS NULL
BEGIN 
	RAISERROR('Media ID cannot be null', 1, 1)
	RETURN 2
END

IF NOT EXISTS(SELECT Username, MediaID FROM Watched WHERE Username = @Username AND MediaID = @MID)
BEGIN 
	RAISERROR('User and Media combination does not exist', 1, 1)
	RETURN 3
END


DELETE [Watched] 
WHERE (Username = @Username AND MediaID = @MID)


GO
/****** Object:  StoredProcedure [dbo].[updActor]    Script Date: 2/16/2023 5:04:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[updActor]
(@ID int, @Name nvarchar(40) = '!', @Gender int = -1)
AS

--Sets values back to default if -1 is given

IF @Fname = '!'
BEGIN
	SET @Name = (SELECT Name FROM Actor WHERE ID = @ID)
END

IF @Gender = -1
BEGIN
	SET @Gender = (SELECT Gender FROM Actor WHERE ID = @ID)
END

--Validations
IF NOT EXISTS(SELECT ID FROM Actor WHERE ID = @ID)
BEGIN 
	RAISERROR('ID does not exist', 1, 1)
	RETURN 1
END

IF @Name IS NULL
BEGIN 
	RAISERROR('Name can not be null', 1, 1)
	RETURN 2
END

IF @Gender <> 0 AND @Gender <> 1
BEGIN 
	RAISERROR('Invalid value for Gender', 1, 1)
	RETURN 4
END

--Set new values

IF @Name <> '!'
BEGIN
	UPDATE Actor
	SET [Name] = @Name
	WHERE (@ID = ID)
	print @@error
END

IF @Gender <> -1
BEGIN
	UPDATE Actor
	SET [Gender] = @Gender
	WHERE (@ID = ID)
	print @@error
END
GO
/****** Object:  StoredProcedure [dbo].[updMedia]    Script Date: 2/16/2023 5:04:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[updMedia]
--(@ID int, @Fname varchar(15) = '!', @Lname varchar(15) = '!', @Gender int = -1)
(@ID int, @Title varchar(15) = '!', @Rating float = -1, @ReleaseDate date = '17530101', @isAdult int = -1)

AS

--Sets values back to default if -1 is given

IF @Title = '!'
BEGIN
	SET @Title = (SELECT Title FROM Media WHERE ID = @ID)
END

IF @Rating = -1
BEGIN
	SET @Rating = (SELECT Rating FROM Media WHERE ID = @ID)
END

IF @ReleaseDate = '17530101'
BEGIN
	SET @ReleaseDate = (SELECT ReleaseDate FROM Media WHERE ID = @ID)
END

IF @isAdult = -1
BEGIN
	SET @isAdult = (SELECT IsAdult FROM Media WHERE ID = @ID)
END

--Validations
IF NOT EXISTS(SELECT ID FROM Media WHERE ID = @ID)
BEGIN 
	RAISERROR('ID does not exist', 1, 1)
	RETURN 1
END

IF @Title IS NULL
BEGIN 
	RAISERROR('Title can not be null', 1, 1)
	RETURN 2
END

IF @ReleaseDate IS NULL
BEGIN 
	RAISERROR('ReleaseDate can not be null', 1, 1)
	RETURN 3
END

IF @isAdult <> 0 AND @isAdult <> 1
BEGIN 
	RAISERROR('Invalid value for isAdult', 1, 1)
	RETURN 4
END

IF @Rating > 5 or @Rating < 0
BEGIN 
	RAISERROR('Rating must be between 0 and 5', 1, 1)
	RETURN 5
END
--Set new values

IF @Title <> '!'
BEGIN
	UPDATE Media
	SET [Title] = @Title
	WHERE (@ID = ID)
	print @@error
END

IF @Rating <> -1
BEGIN
	UPDATE Media
	SET [Rating] = @Rating
	WHERE (@ID = ID)
	print @@error
END

IF @ReleaseDate <> '17530101'
BEGIN
	UPDATE Media
	SET [ReleaseDate] = @ReleaseDate
	WHERE (@ID = ID)
	print @@error
END

IF @isAdult <> -1
BEGIN
	UPDATE Media
	SET [IsAdult] = @isAdult
	WHERE (@ID = ID)
	print @@error
END
GO
/****** Object:  StoredProcedure [dbo].[updMovie]    Script Date: 2/16/2023 5:04:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[updMovie]
--(@ID int, @Fname varchar(15) = '!', @Lname varchar(15) = '!', @Gender int = -1)
(@MediaID int, @Runtime int = -1)

AS

--Sets values back to default if -1 is given

IF @Runtime = -1
BEGIN
	SET @Runtime = (SELECT Runtime FROM Movie WHERE MediaID = @MediaID)
END

--Validations
IF NOT EXISTS(SELECT MediaID FROM Movie WHERE MediaID = @MediaID)
BEGIN 
	RAISERROR('Media ID does not exist', 1, 1)
	RETURN 1
END

IF @Runtime IS NULL
BEGIN 
	RAISERROR('Runtime can not be null', 1, 1)
	RETURN 2
END

--Set new values

IF @Runtime <> -1
BEGIN
	UPDATE Movie
	SET [Runtime] = @Runtime
	WHERE (@MediaID = MediaId)
	print @@error
END

GO
/****** Object:  StoredProcedure [dbo].[updService]    Script Date: 2/16/2023 5:04:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create PROCEDURE [dbo].[updService]
--(@ID int, @Fname varchar(15) = '!', @Lname varchar(15) = '!', @Gender int = -1)
(@ID int, @SName varchar(20) = '!')

AS

--Sets values back to default if -1 is given

IF @SName = '!'
BEGIN
	SET @SName = (SELECT SName FROM StreamingService WHERE ID = @ID)
END

--Validations
IF NOT EXISTS(SELECT ID FROM StreamingService WHERE ID = @ID)
BEGIN 
	RAISERROR('ID does not exist', 1, 1)
	RETURN 1
END

IF @SName IS NULL
BEGIN 
	RAISERROR('SName can not be null', 1, 1)
	RETURN 2
END

--Set new values

IF @SName <> '!'
BEGIN
	UPDATE StreamingService
	SET [SName] = @SName
	WHERE (@ID = ID)
	print @@error
END

GO
/****** Object:  StoredProcedure [dbo].[updShow]    Script Date: 2/16/2023 5:04:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE PROCEDURE [dbo].[updShow]
--(@ID int, @Fname varchar(15) = '!', @Lname varchar(15) = '!', @Gender int = -1)
(@MediaID int, @NumSeasons int = -1, @NumEpisodes int = -1, @LastEpDate date = '17530101')

AS

--Sets values back to default if -1 is given

IF @NumSeasons = -1
BEGIN
	SET @NumSeasons = (SELECT @NumSeasons FROM Show WHERE MediaID = @MediaID)
END

IF @NumEpisodes = -1
BEGIN
	SET @NumEpisodes= (SELECT @NumEpisodes FROM Show WHERE MediaID = @MediaID)
END

IF @LastEpDate = '17530101'
BEGIN
	SET @LastEpDate = (SELECT @LastEpDate FROM Show WHERE MediaID = @MediaID)
END

--Validations
IF NOT EXISTS(SELECT MediaID FROM Show WHERE MediaID = @MediaID)
BEGIN 
	RAISERROR('ID does not exist', 1, 1)
	RETURN 1
END

IF @NumSeasons IS NULL
BEGIN 
	RAISERROR('Number of seasons can not be null', 1, 1)
	RETURN 2
END

IF @NumEpisodes IS NULL
BEGIN 
	RAISERROR('Number of episodes can not be null', 1, 1)
	RETURN 3
END

IF @LastEpDate IS NULL
BEGIN 
	RAISERROR('Last episode date can not be null', 1, 1)
	RETURN 4
END

IF @NumSeasons < 0
BEGIN 
	RAISERROR('Number of seasons can not be negative', 1, 1)
	RETURN 5
END

IF @NumEpisodes < 0
BEGIN 
	RAISERROR('Number of episodes can not be negative', 1, 1)
	RETURN 5
END

IF @LastEpDate < '17530101'
BEGIN 
	RAISERROR('Last episode date can not be before 1753', 1, 1)
	RETURN 6
END
--Set new values

IF @NumSeasons <> -1
BEGIN
	UPDATE Show
	SET [NumSeasons] = @NumSeasons
	WHERE (@MediaID = MediaID)
	print @@error
END

IF @NumEpisodes <> -1
BEGIN
	UPDATE Show
	SET [NumEpisodes] = @NumEpisodes
	WHERE (@MediaID = MediaID)
	print @@error
END

IF @LastEpDate <> '17530101'
BEGIN
	UPDATE Show
	SET [LastEpDate] = @LastEpDate
	WHERE (@MediaID = MediaID)
	print @@error
END
GO
/****** Object:  StoredProcedure [dbo].[updUser]    Script Date: 2/16/2023 5:04:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create PROCEDURE [dbo].[updUser]
--(@ID int, @Fname varchar(15) = '!', @Lname varchar(15) = '!', @Gender int = -1)
(@Username varchar(20), @Password varchar(20) = '!')

AS

--Sets values back to default if -1 is given

IF @Password = '!'
BEGIN
	SET @Password = (SELECT @Password FROM UserProfile WHERE Password = @Password)
END

--Validations
IF NOT EXISTS(SELECT Username FROM UserProfile WHERE Username = @Username)
BEGIN 
	RAISERROR('Username does not exist', 1, 1)
	RETURN 1
END

IF @Password IS NULL
BEGIN 
	RAISERROR('Password can not be null', 1, 1)
	RETURN 2
END

--Set new values

IF @Password <> '!'
BEGIN
	UPDATE UserProfile
	SET [Password] = @Password
	WHERE (@Username = Username)
	print @@error
END

GO
USE [master]
GO
ALTER DATABASE [MediaDB] SET  READ_WRITE 
GO
